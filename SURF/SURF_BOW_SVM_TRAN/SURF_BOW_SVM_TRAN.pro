TEMPLATE = app
CONFIG += console
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += main.cpp \
    FileProcess.cpp \
    Feature.cpp \
    BagOfWord.cpp \
    trainSVM.cpp

INCLUDEPATH+=E:\opencv\include
LIBS+=-LE:\opencv\lib -lopencv_core248 -lopencv_highgui248 -lopencv_imgproc248 -lopencv_features2d248 -lopencv_nonfree248 -lopencv_flann248 -lopencv_ml248 -lopencv_contrib248

HEADERS += \
    FileProcess.h \
    Feature.h \
    BagOfWord.h \
    TrainSVM.h

