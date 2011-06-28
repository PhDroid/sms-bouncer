#!/bin/bash

server="127.0.0.1 5554"

index=0
exec 0<"sms-list.txt"
while read -r line
do
    statement[index]="sms send $line"
    index=$index+1
    break
done

#${arr[*]}         # All of the items in the array
#echo "indexes: ${!statement[*]}"
#echo "count: ${#statement[*]}"

val=`( echo open ${server}
sleep 3
for index in ${!statement[*]}
do
    #set -- $cmd
    echo "${statement[$index]}"
    sleep 1
done
sleep 1) | telnet`
echo $val

exit 0
