#include <iostream>
#include"HogSVM.h"
using namespace std;

int main()
{
    HOGDescriptor *hog=new HOGDescriptor(Size(64,64), Size(16,16), Size(8,8), Size(8,8), 9);
    HogSVM svm("../resources/bg","bg.txt","../resources/leaf","leaf.txt",hog);
    svm.save("../resources/hog.yml");
    return 0;
}

