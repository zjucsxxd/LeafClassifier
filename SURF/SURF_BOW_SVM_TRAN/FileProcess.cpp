#include "FileProcess.h"
FileProcess::FileProcess()
{
}
vector<string>FileProcess::ReadFolderName()
{
    ifstream fin("picture\\category.list");
    vector <string> folderlist;
    while(!fin.eof())
    {
        string folder;
        fin>>folder;
        folderlist.push_back(folder);
    }
    return folderlist;
}

vector <string>FileProcess::ReadAllImage(string dir_path)
{
    FileProcess fileProcess;
    vector<string> imgFullNameList;
    vector<string> folderlist=fileProcess.ReadFolderName();
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
            imgFullNameList.push_back(fullName);
        }
    }
    return imgFullNameList;
}

void FileProcess::UnifyImageSize(cv::Mat& image)
{
    Mat unified_image;
    int s = cv::min(image.rows, image.cols);
    float scale = 128.0 / s;
    Size size(image.cols  * scale, image.rows * scale);
    cv::resize(image, unified_image, size);
    cv::medianBlur(unified_image, unified_image, 3);
    image = unified_image;

}
