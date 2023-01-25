import re
count=0
with open('file.txt', 'r', encoding='utf-8') as file:
    # Read the contents of the file
    lines = file.readlines()
    for line in lines:
        words=line.split()
        
        if(len(words[2])==18):
            count+=1
            print(words[2])

print(count)