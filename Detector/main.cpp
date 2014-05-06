#include"../HogClassifier/HogSVM.h"
int main()
{
    HOGDescriptor *hog=new HOGDescriptor(Size(64,64), Size(16,16), Size(8,8), Size(8,8), 9);
    HogSVM svm("../resources/hog.yml",hog);
    Mat img=imread("../resources/test/t4.jpg");
    Mat gray;
    if(img.channels()>1)
        cvtColor(img,gray,CV_BGR2GRAY);
    else
        gray=img.clone();
    vector<Rect> found;
    svm.detect(gray,found);
    for(int i=0;i<found.size();i++)
    {
        rectangle(img,found[i],Scalar(0,0,255));
    }
    imshow("Detector",img);
    waitKey(0);

    return 0;
}
