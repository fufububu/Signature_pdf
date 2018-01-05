# Signature pdf
*iText* program to insert markers on signature spines when binding books.  
During automatic book stiching sometimes some pages go out of sequence and sognatures go wrong,  
if not rapidly detected you will end with books to dismantle and correct or even worse to throw away if already binded.  
I wrote this program to add a number to each signature in order rapidly check the correctness of stiching  
only looking at the book spine. Looking at book spine should see something like:     
<table><tr><td>
    <img src="https://github.com/fufububu/Signature_pdf/blob/master/Sample_book_spine.png"width="1024" height="150">
</td></tr></table>  

 ## Installation
Dowload into your working directory **Signature_pdf.jar**, **markers** and **sample** folders.  

 ## Example
Lets assume we need to bind an A4 size 96 pages book (*sample.pdf*).  
We first enlarge pages to hold cut marks, insert cut marks and set internal margin on odd and even pages (*Sample step and repeat.pdf*)  
Then create a 48 pages booklet adjusting its dimensions to be printed on SRA3 paper format (*Sample booklet.pdf*) using a 16 pages signature. You will obtain 6 signatures of 4 sheet each:
 * signatue 1 - booklet pages 1-8   - 
 * signatue 2 - booklet pages 9-16  
 * signatue 3 - booklet pages 17-24  
 * signatue 4 - booklet pages 25-32  
 * signatue 5 - booklet pages 33-40  
 * signatue 6 - booklet pages 41-48  

We want markers superimposed on the folding of pages 1, 9, 7, 25, 33, 41 of this file before printing it.  
Copy *Sample booklet.pdf* from *sample* folder in your working directory and execute 
c:\temp\java -jar Signature_pdf.java c:\temp "Sample booklet" "Sample booklet_OK" 6 4  
The ouput file *Sample booklet_OK.pdf* will have required markers and when printed and folded booklet spline will numbered and scaled markers to check correct stiching as shown in figure    
<table><tr><td>
    <img src="https://github.com/fufububu/Signature_pdf/blob/master/Sample_book_spine.png"width="1024" height="150">
</td></tr></table>  

## Usage
TODO: Write usage instructions
## Contributing
1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D
## History
TODO: Write history
## Credits
TODO: Write credits
## License
TODO: Write license
