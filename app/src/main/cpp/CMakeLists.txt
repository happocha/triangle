cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -Wall -Werror")

add_library(triangle SHARED
            nativelib.cpp
            nvg/nanovg.c
        Triangle.cpp)

# Include libraries needed for accelerometergraph lib
target_link_libraries(triangle
                      android
                      GLESv2
                      log)
