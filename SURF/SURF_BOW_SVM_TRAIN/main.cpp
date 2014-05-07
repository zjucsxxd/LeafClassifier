#include <TrainSVM.h>
#include <opencv2/core/core.hpp>
#include <opencv2/nonfree/features2d.hpp>
#include <opencv2/contrib/contrib.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <string>
#include <iostream>
#include <iomanip>
#include <sstream>
#include<fstream>
#include <map>
using namespace std;
using namespace cv;

int main()
{
    //--指明训练的图片路径，MyTrain()返回的是训练好的分类器路径列表

    string dir_path="..\\resource\\";
    string category_path="..\\resource\\category.list";
    vector<string> classifiers;
    TrainSVM trainSVM(dir_path,category_path,classifiers);
    trainSVM.MyTrain();
    return 0;
}

