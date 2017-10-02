import os

path = "/Users/yingweiliu/Desktop/big_data/Circles/"
pathE  = "/Users/yingweiliu/Desktop/big_data/Edges/"
output = open("/Users/yingweiliu/Desktop/circles.csv",'w')

i = 0

files = os.listdir(path)
for i in range(len(files)):
    f = open(path+files[i])
    for line in f:
        set = line.split()
        output.write('\n'+set[0])
        for index in range(1,len(set)-1):
            output.write(','+set[index])

    print files[i]

    f.close()