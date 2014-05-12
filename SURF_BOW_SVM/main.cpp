#include "MyWidget.h"
#include<QApplication>
#include<QtGui>
int main(int argc,char * argv[])
{
    QApplication a(argc,argv);
    MyWidget myWidget;
    myWidget.show();


    return a.exec();
}
