Group: G97
Team Members: Ramesh Suthan Palani,Rahul Aravind Mehalingam,Sanjana Ramakrishnan,Anandan Sundar

List of files:
---------------
a) Distinct.java - for finding the distinct elements in the array
b) Frequent.java - for find the most frequent element in the array
c) CuckooHashSet.java - Implementation of cuckoo Hashing
   HashingFunction.java - Interface for hashing function
   BernsteinHashFunction.java - Implementation of BernsteinHashFunction
   RotationalHashFunction.java - Implementation of RotationalHashFunction

Observations:
--------------
c) Run Time comparison for inserting 1 million numbers.

HashSet Vs CuckooHashSet
------------------------
Total Time taken for Hash Set(ms):932
Total Time taken for cuckooHashing(ms): 3082 with max retry: 1
CuckooHashing Table size:25165824

Total Time taken for Hash Set(ms):931
Total Time taken for cuckooHashing(ms): 2461 with max retry: 3
CuckooHashing Table size:12582912

Total Time taken for Hash Set(ms):1007
Total Time taken for cuckooHashing(ms): 1831 with max retry: 5
CuckooHashing Table size:12582912

Total Time taken for Hash Set(ms):1309
Total Time taken for cuckooHashing(ms): 3592 with max retry: 20
CuckooHashing Table size:12582912

* We can observe that there is difference in performance with change in max retry.
* There is a increase in performance when we increase max retry value from 1 to 5.
  But the performance decreases if we increase the max retry to 20.
* We can also observe that hashing table size is affected by the max retry value.
