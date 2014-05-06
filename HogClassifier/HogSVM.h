#ifndef HOGSVM_H
#define HOGSVM_H
#include<opencv2/opencv.hpp>
#include<string>
#include<vector>
using namespace std;
using namespace cv;
class HogSVM:public CvSVM
{
	private:
        HOGDescriptor *hog;
		void loadPics(string dir,string file,vector<string>& pics);
        void train(vector<string> &leaves, vector<string> &bgs);
        void updateSVMDetector();
	public:
        HogSVM(string leafDir,string leafFile,string bgDir,string bgFile,HOGDescriptor *hog);
        HogSVM(string fileName,HOGDescriptor *hog);
        void detect(Mat&img,vector<Rect>& found);
};
#endif
