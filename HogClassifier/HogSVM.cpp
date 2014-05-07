#include "HogSVM.h"
#include<fstream>
using namespace std;
void HogSVM::loadPics(string file,vector<string>&pics)
{
    Directory dir;
    string imgPath="";
    vector<string> picVec=dir.GetListFiles(file,"*.jpg",false);//false表示不遍历子目录
    for(int i=0;i<picVec.size();i++)
    {
        imgPath=file+"/"+picVec[i];
        pics.push_back(imgPath);
    }
}
HogSVM::HogSVM(string leafFile,string bgFile,HOGDescriptor *hog)
{
    this->hog=hog;
    vector<string> leaves;
    vector<string> bgs;
    loadPics(leafFile,leaves);
    loadPics(bgFile,bgs);
    train(leaves,bgs);
    cout<<"train end"<<endl;
    updateSVMDetector();
}
HogSVM::HogSVM(string fileName,HOGDescriptor *hog)
{
    this->load(fileName.c_str());
    this->hog=hog;
    updateSVMDetector();
}

void HogSVM::updateSVMDetector()
{
    int descriptorDim = this->get_var_count();
    int supportVectorNum = this->get_support_vector_count();
    Mat_<float> alphaMat(1, supportVectorNum);
    Mat_<float> supportVectorMat(supportVectorNum,descriptorDim);
    Mat_<float> resultMat(1, descriptorDim);
    //将alpha向量的数据复制到alphaMat中
    double * pAlphaData =this->decision_func->alpha;
    for(int i=0; i<supportVectorNum; i++)
    {
        alphaMat(0,i) = pAlphaData[i];
    }

    //将支持向量的数据复制到supportVectorMat矩阵中
    for(int i=0; i<supportVectorNum; i++)
    {
        const float * pSVData = this->get_support_vector(i);
        for(int j=0; j<descriptorDim; j++)
        {
            supportVectorMat(i,j)=pSVData[j];
        }
    }
    resultMat=-1*alphaMat*supportVectorMat;
    vector<float> myDetector;
    //将resultMat中的数据复制到数组myDetector中
    for(int i=0; i<descriptorDim; i++)
    {
        myDetector.push_back(resultMat(0,i));
    }
    //最后添加偏移量rho，得到检测子
    myDetector.push_back(this->decision_func->rho);
    hog->setSVMDetector(myDetector);
}

void HogSVM::train(vector<string>&leaves,
                   vector<string>&bgs)
{
    vector<string>::size_type totalNum=leaves.size()+bgs.size();
    Mat_<float> sampleMat(totalNum,hog->getDescriptorSize());
    Mat_<float> labelMat(totalNum,1);
    for(vector<string>::size_type i=0;i<leaves.size();i++)
    {
        labelMat(i,0)=1;
    }
    for(vector<string>::size_type i=leaves.size();i<totalNum;i++)
    {
        labelMat(i,0)=-1;
    }
    for(vector<string>::size_type i=0;i<leaves.size();i++)
    {
        Mat img=imread(leaves[i]);
        cout<<"leaves:"+leaves[i]<<endl;
        vector<float> features;
        hog->compute(img,features);
        for(vector<string>::size_type j=0;j<features.size();j++)
            sampleMat(i,j)=features[j];
    }
    for(vector<string>::size_type i=0;i<bgs.size();i++)
    {
        Mat img=imread(bgs[i]);
        cout<<"bgs:"+bgs[i]<<endl;
        vector<float> features;
        hog->compute(img,features);
        for(vector<float>::size_type j=0;j<features.size();j++)
            sampleMat(i+leaves.size(),j)=features[j];
    }
    CvSVMParams params;
    params.svm_type = CvSVM::C_SVC;
    params.kernel_type = CvSVM::LINEAR;
    params.term_crit = cvTermCriteria(CV_TERMCRIT_ITER, 1000, FLT_EPSILON);
    params.C =1;
    CvSVM::train(sampleMat,labelMat,Mat(),Mat(),params);
}
void HogSVM::detect(Mat&img,vector<Rect>& found)
{
    hog->detectMultiScale(img, found);
}
