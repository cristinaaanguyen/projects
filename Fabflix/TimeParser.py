
file = open("logFile.txt", "r")
ts = 0
tj = 0
amount = 0
for data in file:
        amount += 1
        DataList = data.split(" ")
        tj += int(DataList[1])
        ts += int(DataList[3])
avgTJ = tj/amount
avgTS = ts/amount

print("Avarage TJ " + str(avgTJ / 1000000) + " ms")
print("Average TS " + str(avgTS / 1000000) + " ms")

