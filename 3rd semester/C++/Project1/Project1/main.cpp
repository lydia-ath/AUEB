#include <iostream>
#include <fstream>
#include <cstring>
#include "Image.h"

using namespace std;
using namespace imaging;
int main(int argc, char* argv[]) {
	// ifstream file(, fstream::binary );
	Image * im = new Image();

	string neg = argv[1];
	string filename="";
	if (argc > 2) {
	 filename = argv[2];
	}
	string input_filename = " ";
	string output_filename = "photo";
	
	// case where the user only gives the neg as input
	if (filename == "") {
		cout << " File name of the image to load:  ";
		cin >> input_filename;
		filename = input_filename.substr(6, 1);
		string append = "_neg.ppm";
		output_filename.append(filename);
		output_filename.append(append);

	}
	// case where the user gives neg and the photo to load
	else {
		
		input_filename = filename;
		filename = filename.substr(6, 1);
		string append = "_neg.ppm";
		output_filename.append(filename);
		output_filename.append(append);
	}




	if (im->load(input_filename, "ppm")) {
		cout << "Image dimensions are: " << im->getWidth() << " X " << im->getHeight() << endl;
		im->save(output_filename, "ppm");
	}

	delete im;

	system("pause");

	return 0;

}