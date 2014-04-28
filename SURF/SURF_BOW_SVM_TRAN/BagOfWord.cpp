#include "BagOfWord.h"
#include "FileProcess.h"
BagOfWord::BagOfWord()
{
}
 void BagOfWord::ConstractBoW(Ptr<FeatureDetector> &detector,BOWImgDescriptorExtractor &bowExtractor, map<string,Mat>& samples)
{

    FileProcess fileProcess;
    string dir_path="picture\\";
    vector<string> folderlist=fileProcess.ReadFolderName();
    vector<KeyPoint> keyPoints;
    Mat descriptors;
    for(int i=0;i<folderlist.size();i++)
    {
        string folder=folderlist[i];
        string path=dir_path+folder;
        Directory dir;
        vector<string> imgNames=dir.GetListFiles(path,"*.jpg",false);
        for(int j=0;j<imgNames.size();j++)
        {
            string imgName=imgNames[j];
            string fullName=path+"\\"+imgName;
            cout<<fullName<<endl;
            Mat img=imread(fullName);
            fileProcess.UnifyImageSize(img);

            detector->detect(img,keyPoints);
            bowExtractor.compute(img,keyPoints,descriptors);
            if(samples.count(folder)==0)
                samples[folder].create(0,descriptors.cols,descriptors.type());
            samples[folder].push_back(descriptors);
        }
    }
    FileStorage fin ("picture\\samples.yml",FileStorage::WRITE);
    map<string,Mat>::iterator iter;
    for(iter=samples.begin();iter!=samples.end();++iter)
    {
        fin<<"lable"+ iter->first<<iter->second;
        cout<<iter->first<<endl;
    }
    fin.release();

}


