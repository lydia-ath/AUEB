#include "Image.h"
using namespace imaging;

class Filter {
	virtual Image operator << (const Image & image)=0;
};

