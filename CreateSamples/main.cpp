#include <iostream>
#include<opencv2/opencv.hpp>
#include <highgui.h>
#include<ml.h>
#include<fstream>
#include"Samples.h"
using namespace std;
using namespace cv;
void ImgPrePro(string imgname);
int main()
{
    Samples samples;
    samples.ImgPrePro("../resources/f1","../resources/f2");
    cout<<"ok"<<endl;
    return 0;
}

