#Importing Required Library Files

import math
import sys
import os
from tkinter import *
import numpy as np
import cv2,os
from scipy.signal import welch
import datetime,time
from statistics import stdev,mean
from tkinter import *
import matplotlib.pyplot as plt




def heart_rate():    #Function to find heart rate
    framerate = 30    #Specifying Frame Rate
    nsegments = 12
    cap = cv2.VideoCapture('VID_20190303_172134.mp4') #Load Video into python idle
    global img_size
    ret,frame = cap.read() #Reading 1st frame
    img_size = frame.size  #Calculating size of Frame
    length = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))  #Finding total no. of frames

    b_av = np.mean(frame[:,:,0])   #Calculating average value of Blue channel in frame
    g_av = np.mean(frame[:,:,1]) # calculating same for green channel
    r_av = np.mean(frame[:,:,2]) #calculating same for red channel

    mean_rgb = np.array( [ r_av , g_av , b_av ] )   #Initialising Numpy Array to store array containing average value of all 3 channels in each frame
    ctr = 0
    while ( cap.isOpened() and ctr<length-1):   #Looping till all frames are processed
        ret , frame = cap.read()  #Read Frame
        b_av = np.mean ( frame[:,:,0] ) #Blue Channel Average
        g_av = np.mean ( frame[:,:,1] ) #Green Channel Average
        r_av = np.mean ( frame[:,:,2] ) #Red Channel Average
        mean_rgb = np.vstack ( ( mean_rgb , np.array ( [ r_av , g_av , b_av] ) ) ) #Appending numpy array to array containing average values in each frame
        ctr += 1

    cap.release()  # Releasing Video Pointer
    l = int ( framerate * 1.6)  #Initialising size of window

    

    for t in range(0, mean_rgb.shape[0]-l):
        #Step 1: Spatial Averaging

        C = mean_rgb[t:t+l-1,:].T #Finding average of all 3 channels individually in the window
       

        #Temporal Normalization
        mean_colour = np.mean(C, axis=1) #Finding mean colour
      
        diag_mean_colour = np.diag(mean_colour) 
        
        diag_mean_colour_inv = np.linalg.inv(diag_mean_colour)
        
        Cn = np.matmul(diag_mean_colour_inv,C) #Normalise R,G,B values in each frame of window
        
        #Step 3
            
        projection_matrix = np.array ( [[0,1,-1],[-2,1,1]])  #Finding Projection Matrix that represents the 2 planes perpendicular to skin

        S = np.matmul(projection_matrix , Cn) #Finding projection of normalised value on projection planes
        
        #Step 4 2D to 1D

        std = np.array([1,np.std(S[0,:])/np.std(S[1,:])]) #Std. Deviation of 1st row / that of row 2
        
        P = np.matmul(std,S) #Std. Deviation in the specified window
        

        #Step 5 Overlap-Adding

        H = np.zeros ( mean_rgb.shape[0] )
        
        P = P-mean(P)
        H[t:t+l-1] = H[t:t+l-1] + P  #Adds Std. Deviation in window to a array carrying signal components of each frame

        if t != 0:
            signal = np.vstack((signal,H)) #Appending signal obtained in window to array storing entire signal
        else:
            signal = H  #Initailising Array to store actual processed signal
        
    # Plots Signal        
    plt.plot(range(signal.shape[0],signal,'g'))
    plt.show()
             
    
    

    segment_length = (2*signal.shape[0]) // (nsegments)

    signal = signal.flatten()  #Flattens Signal for Power Spectral Density Calculation
    

    green_f , green_psd = welch(signal , framerate , 'flattop', nperseg = segment_length) #Finding PSD by Welch Method

    

    first = np.where(green_f > 1)[0]  #Finds first freq. component > lower cutoff 1Hz
    
    last = np.where(green_f < 3)[0] #Finds last freq. component < Upper Cutoff 3Hz
    
    first_index = first[0]
    
    last_index = last[-1]
    
    range_of_interest = range(first_index,last_index+1,1) #Finds all frequencies in the range
    
    max_idx = np.argmax(green_psd[range_of_interest]) #Finds Index with Max Power
    
    f_max = green_f[range_of_interest[max_idx]] #Finds Freq. of Highest Power
    hr = f_max*60.0 # HR in bpm = Freq. * 60
    print(hr)
    var.set(hr) 
    file=open("test.txt","w") #Opens text file to store heart rate which is sent to phone via local server
    file.write(f"{hr}\n") #Writes the HR in the file
    file.close() #Closes The file

# Graphical User Interface Part using Tkinter Module
window = Tk()
window.title("Heart Rate Estimate")
window.geometry("250x250")


btn = Button(window, text="Calculate",width=15,height=3, command=heart_rate)
btn.pack()


var = StringVar()
var.set("Output") # <-- Set to the initial String that you want the label to have

lbl3 = Label(window, textvariable=var)
lbl3.pack()
window.mainloop()
