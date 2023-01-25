package com.example.ecrypt_decrypt_stanley

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.File
import kotlin.random.Random
import kotlin.text.Regex
class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    fun encrypt(text: String,emojiList:MutableList<String>):String{
        if(text.isEmpty()) return ""
        var encryptedStr = ""
        val random = Random
        var randomInt = random.nextInt(0, emojiList.size-1)
        var check = false
        var nowLetter : Int
        var nextLetter : Int
        var secondcheck = false
        encryptedStr += emojiList[randomInt]
        for(i in 0 until text.length){
            if(check){
                check=false
                secondcheck = true
                continue
            }
            nowLetter = 126-text[i].toInt()
            if(nowLetter >= 7 && nowLetter<23 && i<text.length-1 && !secondcheck){
                nextLetter = 126-text[i+1].toInt()
                if(nextLetter>=7 && nextLetter<23) {
                    randomInt+=nowLetter*23+nextLetter - 46
                    check=true
                }
            }
            if(!check){
                randomInt+=nowLetter
            }
            if(randomInt>712){
                randomInt%=712
            }
            secondcheck = false
            encryptedStr += emojiList[randomInt]
        }
        val firstTwo = encryptedStr.slice(0..1)
        val secondTwo = encryptedStr.slice(2..3)
        val resultStr = secondTwo + firstTwo + encryptedStr.slice(4..encryptedStr.lastIndex)
        return resultStr
    }

    fun binarySearch(list: MutableList<String>, c: String): Int {
        var left = 0
        var right = list.size - 1
        while (left <= right) {
            val mid = (left + right) / 2
            val midChar = list[mid]
            when {
                c == midChar -> return mid
                c < midChar -> right = mid - 1
                else -> left = mid + 1
            }
        }
        return -1
    }
    fun decrypt(text: String,emojiList:MutableList<String>):String{
        if (text.length==0) return ""
        var ans=""
        var first = binarySearch(emojiList,text[2].toString()+text[3].toString())
        var shift = 0
        var ascii1 = 0
        var ascii2 = 0
        for(i in 0 until text.length/2){
            if(i==1) continue
            var tmp = binarySearch(emojiList,text[2*i].toString()+text[2*i+1].toString())
            shift = (tmp-first+712)%712
            if(shift>121){
                ascii1 = (shift+46)/23
                ascii2 = shift % 23
                ascii1 = 126-ascii1
                ascii2 = 126-ascii2
                ans += ascii1.toChar().toString() + ascii2.toChar().toString()
            }
            else{
                ascii1 = 126-shift
                ans += ascii1.toChar().toString()
            }
            first = (first+shift)%712

        }
        return ans
    }
    fun quickSort(list: MutableList<String>, left: Int, right: Int) {
        if (left < right) {
            val pivotIndex = partition(list, left, right)
            quickSort(list, left, pivotIndex)
            quickSort(list, pivotIndex + 1, right)
        }
    }
    private fun partition(list: MutableList<String>, left: Int, right: Int): Int {
        val pivot = list[(left + right) / 2]
        var i = left - 1
        var j = right + 1
        while (true) {
            do {
                i++
            } while (list[i] < pivot)
            do {
                j--
            } while (list[j] > pivot)
            if (i >= j) {
                return j
            }
            val temp = list[i]
            list[i] = list[j]
            list[j] = temp
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_encrypt = findViewById<Button>(R.id.btn_encrypt)
        val btn_decrypt = findViewById<Button>(R.id.btn_decrypt)
        val input_words = findViewById<TextInputEditText>(R.id.input_words)
        val input_emojis = findViewById<TextInputEditText>(R.id.input_emoji)
        val textview_emojis = findViewById<TextInputLayout>(R.id.text_Layout_emojis)
        val textview_words = findViewById<TextInputLayout>(R.id.text_layout_words)

        val emojiList = mutableListOf("😁","😂","😃","😄","😅","😆","😉","😊","😋","😌","😍","😏","😒","😓","😔","😖","😘","😚","😜","😝","😞","😠","😡","😢","😣","😤","😥","😨","😩","😪","😫","😭","😰","😱","😲","😳","😵","😷","😸","😹","😺","😻","😼","😽","😾","😿","🙀","🙅","🙆","🙇","🙈","🙉","🙊","🙋","🙌","🙍","🙎","🙏","😀","😇","😈","😎","😐","😑","😕","😗","😙","😛","😦","😧","😬","😮","😯","😴","😶","🚀","🚃","🚄","🚅","🚇","🚉","🚌","🚏","🚑","🚒","🚓","🚕","🚗","🚙","🚚","🚢","🚤","🚥","🚧","🚨","🚩","🚪","🚫","🚬","🚭","🚲","🚶","🚹","🚺","🚻","🚼","🚽","🚾","🛀","🚁","🚂","🚆","🚈","🚊","🚍","🚎","🚐","🚔","🚖","🚘","🚛","🚜","🚝","🚞","🚟","🚠","🚡","🚣","🚦","🚮","🚯","🚰","🚱","🚳","🚴","🚵","🚷","🚸","🚿","🛁","🛂","🛃","🛄","🛅","🅰" ,"🅱" ,"🅾" ,"🅿" ,"🆎","🆑","🆒","🆓","🆔","🆕","🆖","🆗","🆘","🆙","🆚","🈁","🈂","🈚","🈯","🈲","🈳","🈴","🈵","🈶","🈷","🈸","🈹","🈺","🉐","🉑","8⃣","9⃣","7⃣","6⃣","1⃣","0⃣","2⃣","3⃣","5⃣","4⃣","#⃣","🀄","🃏","🌀","🌁","🌂","🌃","🌄","🌅","🌆","🌇","🌈","🌉","🌊","🌋","🌌","🌏","🌑","🌓","🌔","🌕","🌙","🌛","🌟","🌠","🌰","🌱","🌴","🌵","🌷","🌸","🌹","🌺","🌻","🌼","🌽","🌾","🌿","🍀","🍁","🍂","🍃","🍄","🍅","🍆","🍇","🍈","🍉","🍊","🍌","🍍","🍎","🍏","🍑","🍓","🍔","🍕","🍖","🍗","🍘","🍙","🍚","🍛","🍜","🍝","🍞","🍟","🍠","🍡","🍢","🍣","🍤","🍥","🍦","🍧","🍨","🍩","🍪","🍫","🍬","🍭","🍮","🍯","🍰","🍱","🍲","🍳","🍴","🍵","🍶","🍷","🍸","🍹","🍺","🍻","🎀","🎁","🎂","🎃","🎄","🎅","🎆","🎇","🎈","🎉","🎊","🎋","🎌","🎍","🎎","🎏","🎐","🎑","🎒","🎓","🎠","🎡","🎢","🎣","🎤","🎥","🎦","🎧","🎨","🎩","🎪","🎫","🎬","🎭","🎮","🎯","🎰","🎱","🎲","🎳","🎴","🎵","🎶","🎷","🎸","🎹","🎺","🎻","🎼","🎽","🎾","🎿","🏀","🏁","🏂","🏃","🏄","🏆","🏈","🏊","🏠","🏡","🏢","🏣","🏥","🏦","🏧","🏨","🏩","🏪","🏫","🏬","🏭","🏮","🏯","🏰","🐌","🐍","🐎","🐑","🐒","🐔","🐗","🐘","🐙","🐚","🐛","🐜","🐝","🐞","🐟","🐠","🐡","🐢","🐣","🐤","🐥","🐦","🐧","🐨","🐩","🐫","🐬","🐭","🐮","🐯","🐰","🐱","🐲","🐳","🐴","🐵","🐶","🐷","🐸","🐹","🐺","🐻","🐼","🐽","🐾","👀","👂","👃","👄","👅","👆","👇","👈","👊","👋","👌","👍","👎","👏","👐","👑","👒","👓","👔","👕","👖","👗","👘","👙","👚","👛","👜","👝","👞","👟","👠","👡","👢","👣","👤","👦","👧","👨","👩","👪","👫","👮","👯","👰","👱","👲","👳","👴","👵","👶","👷","👸","👹","👺","👻","👼","👽","👾","👿","💀","💁","💂","💃","💄","💅","💆","💇","💈","💉","💊","💋","💌","💍","💎","💏","💐","💑","💒","💓","💔","💕","💖","💗","💘","💙","💚","💛","💜","💝","💞","💟","💠","💡","💢","💣","💤","💥","💦","💧","💨","💩","💪","💫","💬","💮","💯","💰","💱","💲","💳","💴","💵","💸","💹","💺","💻","💼","💽","💾","💿","📀","📁","📂","📃","📄","📅","📆","📇","📈","📉","📊","📋","📌","📍","📎","📏","📐","📑","📒","📓","📔","📕","📖","📗","📘","📙","📚","📛","📜","📝","📞","📟","📠","📡","📢","📣","📤","📥","📦","📧","📨","📩","📪","📫","📮","📰","📱","📲","📳","📴","📶","📷","📹","📺","📻","📼","🔃","🔊","🔌","🔍","🔎","🔏","🔐","🔑","🔒","🔓","🔔","🔖","🔗","🔘","🔙","🔚","🔛","🔜","🔝","🔞","🔟","🔠","🔡","🔢","🔣","🔤","🔥","🔦","🔧","🔨","🔩","🔪","🔫","🔮","🔯","🔰","🔱","🔲","🔳","🔴","🔵","🔶","🔷","🔸","🔹","🔺","🔻","🔼","🔽","🕐","🕑","🕒","🕓","🕔","🕕","🕖","🕗","🕘","🕙","🕚","🕛","🗻","🗼","🗽","🗾","🗿","🌍","🌎","🌐","🌒","🌖","🌗","🌘","🌚","🌜","🌝","🌞","🌲","🌳","🍋","🍐","🍼","🏇","🏉","🏤","🐀","🐁","🐂","🐃","🐄","🐅","🐆","🐇","🐈","🐉","🐊","🐋","🐏","🐐","🐓","🐕","🐖","🐪","👥","👬","👭","💭","💶","💷","📬","📭","📯","📵","🔀","🔁","🔂","🔄","🔅","🔆","🔇","🔉","🔕","🔬","🔭","🕜","🕝","🕞","🕟","🕠","🕡","🕢","🕣","🕤","🕥","🕦","🕧")
        quickSort(emojiList,0,711)

        btn_encrypt.setOnClickListener {
            val text = input_words.text.toString()
            val returnText = encrypt(text,emojiList)
            input_emojis.setText(returnText)
        }

        btn_decrypt.setOnClickListener {
            val emojis = input_emojis.text.toString()
            val returnEmoji = decrypt(emojis,emojiList)
            input_words.setText(returnEmoji)
        }
    }
}