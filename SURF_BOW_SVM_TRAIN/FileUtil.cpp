#include "FileUtil.h"
FileUtil::FileUtil()
{
}
vector<string> FileUtil::ReadFolderName()
{
    ifstream fin("..\\resource\\category.list");
    vector <string> folderlist;
    while(!fin.eof())
    {
        string folder;
        fin>>folder;
        folderlist.push_back(folder);
        cout<<folder<<endl;
    }
    return folderlist;

}
vector<string> FileUtil::ReadAllImage(string dir_path)
{
    vector<string> imgFullNameList;
    vector<string> folderlist=this->ReadFolderName();
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
            cout<<fullName<<endl;

        }
    }
    return imgFullNameList;
}
void FileUtil::UnifyImageSize(Mat &image)
{
    Mat unified_image;
    int s = cv::min(image.rows, image.cols);
    float scale = 128.0 / s;
    Size size(image.cols  * scale, image.rows * scale);
    cv::resize(image, unified_image, size);
    cv::medianBlur(unified_image, unified_image, 3);
    image = unified_image;

}
