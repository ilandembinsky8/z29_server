import csv


inFile = open("campaigns_kpi.csv","r")
reader = csv.reader(inFile)
outFile = open("temp.csv","w")
writer = csv.writer(outFile)

for row in reader:
	writer.writerow([row[0], row[1], row[3], row[11], row[15], row[16]])

inFile.close()
outFile.close()

inFile = open("temp.csv","r")
outFile = open("exch_creative.csv","w")

tempList = set()

for row in inFile:
	if row in tempList:
		continue
	else:
		tempList.add(row)
		outFile.write(row)


outFile.close()
inFile.close()

