#include "Samples.h"
void Samples::LoadPic(string sourceFile, vector<string> &pics)
{
    Directory dir;
    //string imgPath="";
    vector<string> picVec=dir.GetListFiles(sourceFile,"*.jpg",false);//false表示不遍历子目录
    for(int i=0;i<picVec.size();i++)
    {
        //imgPath=file+"/"+picVec[i];
        //pics.push_back(imgPath);
        pics.push_back(picVec[i]);
    }
}
void::Samples::ImgPrePro(string sourceFile, string saveFile)
{
    vector<string> pic;
    LoadPic(sourceFile,pic);
    Mat src,dst,gray;
    string imgpath="";
    for(int i=0;i<pic.size();i++)
    {
        imgpath=sourceFile+"/"+pic[i];
        src=imread(imgpath);

        //尺寸处理为64*64
        Size dsize = Size(64,64);
        dst = Mat(dsize,CV_32S);
        resize(src,dst,dsize);

        //灰度化处理
        //Mat gray;
        cvtColor(dst,gray,CV_BGR2GRAY);
        imgpath=saveFile+"/"+pic[i];
        imwrite(imgpath,gray);
    }
}
