# Signature pdf
*iText5* program to insert markers on signature spines when binding books.  
During automatic book stiching sometimes some pages go out of sequence and signatures will be wrong,  
if not rapidly detected you will end with books to dismantle and correct or even worse to throw away if already binded.  
I wrote this program to add a number to each signature in order to rapidly check the correctness of stiching process 
only examining at the book spine. 

 ## Prerequisites

Developed and tested with:
 + Windows 7 64 bit
 + NetBeans IDE 8.0.2
 + java version "1.8.0_25"
 + Java(TM) SE Runtime Environment (build 1.8.0_25-b18)
 + itext5-itextpdf-5.5.12.jar library [(https://github.com/itext/itextpdf/releases/tag/5.5.12)]  
 + javax.swing library
    
 ## Installing
If you want to compile/modify simply download Signature.java source, create a NetBeans or Eclpse IDE project, add *itext5-itextpdf-5.5.12.jar* library and add/modify and compile  

Otherwise download into your working directory **Signature_pdf.jar**, **markers** and **sample** folders and run it typing:  
  
    java -jar Signature_pdf.jar <working directory> <input filename> <output filename> <signature number> <signature sheets> <markers filename>   

where:  
 -working directory: path where are located *\*.jar* program, *marker* folder and file to be processed  
 -input filename   : file name to be processed (without .pdf extension)  
 -output filename  : processede file name  
 -signature number : input file number of signatures  
 -signature sheets : signature number of sheets  
 -markers filename : optional markers flename (defaukt "marker")  
 ![alt text](https://github.com/fufububu/Signature_pdf/blob/master/Signature_pdf1.png)  
 
 If you run without command line parameters a window will open to ask parameters, markers filename is optional
  
 ![alt text](https://github.com/fufububu/Signature_pdf/blob/master/Signature_pdf2.png)  
 ![alt text](https://github.com/fufububu/Signature_pdf/blob/master/Signature_pdf3.png)  
 ![alt text](https://github.com/fufububu/Signature_pdf/blob/master/Signature_pdf4.png)  
 
    
 ## Requirements
 


 ## Example
Lets assume we need to bind an A4 size 96 pages book (*sample.pdf*).  
We first enlarge pages to hold cut marks, insert cut marks and set internal margin on odd and even pages (*Sample step and repeat.pdf*)  
Then create a 48 pages booklet adjusting its dimensions to be printed on SRA3 paper format (*Sample booklet.pdf*) using a 16 pages signature. You will obtain 6 signatures of 4 sheet each:
 * signatue 1 - booklet pages 1-8   
 * signatue 2 - booklet pages 9-16  
 * signatue 3 - booklet pages 17-24  
 * signatue 4 - booklet pages 25-32  
 * signatue 5 - booklet pages 33-40  
 * signatue 6 - booklet pages 41-48  

We want markers superimposed on the folding of pages 1, 9, 7, 25, 33, 41 of this file before printing it.  
Copy *Sample booklet.pdf* from *sample* folder in your working directory and execute 
c:\temp\java -jar Signature_pdf.jar c:\temp "Sample booklet" "Sample booklet_OK" 6 4  
The ouput file *Sample booklet_OK.pdf* will contain the required markers and when printed and folded the booklet spline will show numbered and scaled markers to check correct stiching as in figure:  
<table><tr><td>
    <img src="https://github.com/fufububu/Signature_pdf/blob/master/Sample_book_spine.png"width="1024" height="150">
</td></tr></table>  
