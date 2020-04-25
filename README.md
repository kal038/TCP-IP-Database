This program will create a database object from an input csv file.
This database holds instances of OBAJWorker which each represent a worker.
Each worker has different attributes such as an ID, DOB, Marital Status, etc.
When  interacting with the database, the user has multiple options on what to do with 
the data. 
When the user first runs the program, they will be prompted to enter the name of the
csv file that they wish to use. If they enter a valid file, then that file will be used.
If the file is invalid, then the default csv file will be used. After that, the user will
be prompted to either enter in a time in the future or to use the current time. If an
invalid future time is entered, then the current time will be used. Using a future time
will result in the workers in the database to get updated based on this new time.
After the time has been set, the user will be prompted with 5 different functions to 
perform. They can either query the database by four different attributes, sort the
workers by worker ID, add/modify a worker, undo their last change, or save the database
to the save file. They may also quit the program from this menu. 

NOTE: Most of the interface is done through the console, with the exception being when
the user is asked to input a time or select the system time.

NOTE: Test cases have been added and some methods do not have test cases since they are
proven to be functional by the testing of other methods which call them.

Authors: Parker Messner and Khoi Lam