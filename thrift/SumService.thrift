namespace java com.studycode.thrift
service SumService{
    i32 getSum(1:i32 num1, 2:i32 num2)
}