#include <TrainSVM.h>
#include <FileUtil.h>
#include <iostream>
#include <opencv2/core/core.hpp>
#include <string>
#include <vector>
#include <iostream>
#include <iomanip>
#include <sstream>
#include<fstream>

using namespace std;
using namespace cv;

int main()
{
    string dir_path="..\\resource\\";
    TrainSVM trainSVM(dir_path);
    map<string, Mat> samples;
    trainSVM.ConstractBoW(samples);
    trainSVM.Train(samples);
    cout<<"game over"<<endl;
    return 0;

}

