#include "MyWidget.h"
#include<QApplication>
int main(int argc,char * argv[])
{
    QApplication a(argc,argv);
    MyWidget m;
    m.show();
    return a.exec();
}
