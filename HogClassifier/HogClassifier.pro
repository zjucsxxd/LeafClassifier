TEMPLATE = app
CONFIG += console
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += main.cpp \
    HogSVM.cpp

HEADERS += \
    HogSVM.h
win32{
    INCLUDEPATH+=$(OPENCV_ROOT)\include
    LIBS+=-L$(OPENCV_ROOT)\lib -lopencv_core248 -lopencv_highgui248 -lopencv_imgproc248 -lopencv_features2d248 -lopencv_nonfree248 -lopencv_flann248 -lopencv_ml248 -lopencv_contrib248
}
unix{
    INCLUDEPATH+=$(OPENCV_ROOT)/include
    LIBS+=-L$(OPENCV_ROOT)/lib -lopencv_core -lopencv_highgui -lopencv_imgproc -lopencv_features2d -lopencv_nonfree -lopencv_flann -lopencv_ml -lopencv_contrib -lopencv_calib3d -lopencv_contrib -lopencv_gpu -lopencv_legacy -lopencv_objdetect -lopencv_ocl -lopencv_photo -lopencv_stitching -lopencv_superres -lopencv_video -lopencv_videostab
}
