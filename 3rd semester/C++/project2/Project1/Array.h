#ifndef _Array_
#define _Array_

#include <vector>
#include <cstddef>

namespace math {


	template <class T>
	class Array {


	protected:
		std::vector<T>  buffer;                              //! Holds the image data.

		unsigned int width, 						 //! The width of the image (in pixels)
			height;		                 //! The height of the image (in pixels)

	public:
		// metric accessors

		/*! Returns the width of the image
		*/
		const unsigned int getWidth() const { return width; }

		/*! Returns the height of the image
		*/
		const unsigned int getHeight() const { return height; }

		// data accessors

		/*! Obtains a pointer to the internal data.
		*
		*  This is NOT a copy of the internal image data, but rather a pointer
		*  to the internally allocated space, so DO NOT attempt to delete the pointer.
		*/
		T * getRawDataPtr() {
			return buffer;
		}

		/*! Obtains the color of the image at location (x,y).
		*
		*  The method should do any necessary bounds checking.
		*
		*  \param x is the (zero-based) horizontal index of the pixel to get.
		*  \param y is the (zero-based) vertical index of the pixel to get.
		*
		*  \return The color of the (x,y) pixel as a Color object. Returns a black (0,0,0) color in case of an out-of-bounds x,y pair.
		*/
		T getPixel(unsigned int x, unsigned int y) const {
			Color* col = new Color();
			if ((x <= width) && (y <= height) && (x*y >= 0)) {

				col->b = width*y + x;
				col->g = width*y + x + 1;
				col->r = width*y + x + 2;

			}
			return *col;
		}

		// data mutators

		/*! Sets the RGB values for an (x,y) pixel.
		*
		*  The method should perform any necessary bounds checking.
		*
		*  \param x is the (zero-based) horizontal index of the pixel to set.
		*  \param y is the (zero-based) vertical index of the pixel to set.
		*  \param value is the new color for the (x,y) pixel.
		*/
		void setPixel(unsigned int x, unsigned int y, T & value) {
			if ((x <= width) && (y <= height) && (x*y >= 0)) {
				(buffer + width*y + x)->r = value.r;
				(buffer + width*y + x + 1)->g = value.g;
				(buffer + width*y + x + 2)->b = value.b;
			}
			else {
				cout << "Width or heigth out of bounds!" << endl;
			}
		}

		/*! Copies the image data from an external raw buffer to the internal image buffer.
		*
		*  The member function ASSUMES that the input buffer is of a size compatible with the internal storage of the
		*  Image object and that the data buffer has been already allocated. If the image buffer is not allocated or the
		*  width or height of the image are 0, the method should exit immediately.
		*
		*  \param data_ptr is the reference to the preallocated buffer from where to copy the data to the Image object.
		*/
		void setData(const T * & data_ptr) {
			int size = width * height;
			buffer = new Color[size];

			for (int i = 0; i < size; i++) {
				buffer[i] = data_ptr[i];
			}
		}

		// constructors and destructor

		/*! Default constructor.
		*
		* By default, the dimensions of the image should be zero and the buffer must be set to nullptr.
		*/
		Array() : width(0), height(0), buffer(NULL) {}

		/*! Constructor with width and height specification.
		*
		* \param width is the desired width of the new image.
		* \param height is the desired height of the new image.
		*/
		Array(unsigned int width, unsigned int height) {
			this->width = width;
			this->height = height;
			buffer = 0;
		}

		/*! Constructor with data initialization.
		*
		* \param width is the desired width of the new image.
		* \param height is the desired height of the new image.
		* \param data_ptr is the source of the data to copy to the internal image buffer.
		*
		* \see setData
		*/
		Array(unsigned int width, unsigned int height, const T * data_ptr) : {
			this->width = width;
			this->height = height;
			setData(data_ptr);
		}

		/*! Copy constructor.
		*
		* \param src is the source image to replicate in this object.
		*/
		Array(const Array &src) :width(src.width), height(src.height), buffer(src.buffer) {}


		/*! The Image destructor.
		*/
		~Array() {
			buffer.clear();
		}

		/*! Copy assignment operator.
		*
		* \param right is the source image.
		*/
		Array & operator = (const Array & right) {
			Array  left = Array(right);
			return left;
		}

		Array &operator () (const Array & arr) {

		}

	};



}

#endif