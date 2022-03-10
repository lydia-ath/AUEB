#include "Image.h"
#include "../ppm/ppm.h"
#include <fstream>
#include <iostream>
#include <cstring>
using namespace std;
namespace imaging

	// Image class implementation goes here//
{


	Color * Image::getRawDataPtr()
	{
		return buffer;
	}

	Color Image::getPixel(unsigned int x, unsigned int y) const
	{
		Color* col = new Color();
		if ((x <= width) && (y <= height) && (x*y >= 0)) {

			col->b = width*y + x;
			col->g = width*y + x + 1;
			col->r = width*y + x + 2;

		}
		return *col;

	}

	void Image::setPixel(unsigned int x, unsigned int y, Color & value) {
		//elegxos
		if ((x <= width) && (y <= height) && (x*y >= 0)) {
			(buffer + width*y + x)->r = value.r;
			(buffer + width*y + x + 1)->g = value.g;
			(buffer + width*y + x + 2)->b = value.b;
		}
		else {
			cout << "Width or heigth out of bounds!" << endl;
		}
	}

	void Image::setData(const Color *& data_ptr) {
		int size = width * height;
		buffer = new Color[size];

		for (int i = 0; i < size; i++) {
			buffer[i] = data_ptr[i];
		}

	}

	Image::Image() : width(0), height(0), buffer(nullptr) {}

	Image::Image(unsigned int width, unsigned int height) {
		this->width = width;
		this->height = height;
		buffer = 0;
	}

	Image::Image(unsigned int width, unsigned int height, const Color * data_ptr) {
		this->width = width;
		this->height = height;
		setData(data_ptr);

	}

	Image::Image(const Image & src) :width(src.width), height(src.height), buffer(src.buffer) {}

	Image::~Image() {
		delete[] buffer;
	}

	Image & Image::operator=(const Image & right)
	{
		Image  left = Image(right);
		return left;
	}

	bool Image::load(const std::string & filename, const std::string & format) {

		ifstream ifs(filename, ios::in | ios::binary);
		if (ifs.is_open()) {

			string f;
			ifs >> f;
			ifs >> width;
			ifs >> height;
			ifs.close();
			int *w = new int;
			int *h = new int;
			*w = width;
			*h = height;
			const char * c = filename.c_str();
			float *buf = ReadPPM(c, w, h);
			unsigned char *b = reinterpret_cast<unsigned char *>(buf);
			if (buf != NULL) {
				buffer = new Color[width * height];
				Color *neg = new Color(1, 1, 1);

				for (int i = 0; i < width * height; i++) {
					buffer[i].r = (float)b[3 * i] / 255;
					buffer[i].g = (float)b[3 * i + 1] / 255;
					buffer[i].b = (float)b[3 * i + 2] / 255;
					buffer[i] = neg[0].operator-(buffer[i]);
				}

				return true;
			}
		}
		return false;

	}

	bool Image::save(const std::string & filename, const std::string & format) {
		if (buffer == nullptr || format != "ppm" || format != "PPM") return false;
		float * buf;
		unsigned char* b = new unsigned char[3 * width*height];
		for (int i = 0; i<width*height; i++) {
			b[3 * i] = (float)buffer[i].r * 255;
			b[3 * i + 1] = (float)buffer[i].g * 255;
			b[3 * i + 2] = (float)buffer[i].b * 255;
		}

		buf = reinterpret_cast<float *>(b);
		const char * form = filename.c_str();
		if(WritePPM(buf, width, height, form)) return true;
		return false;
	}





}