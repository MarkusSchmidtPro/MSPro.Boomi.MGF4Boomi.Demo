# Create a Customer Project

I recommend you create a project per customer - per Boomi Account.

- Create a folder on your local drive - we will call it `%ProjectRoot%`.
- Create a `lib` folder inside
    - [Extract the Groovy distribution](https://www.notion.so/Preparation-c8092a0505bf4333aa325b31ed2f0d6f?pvs=21) to the `lib` folder

![Untitled](Create%20a%20Customer%20Project%202df4a711ccc44a5e8093d52c50fa43a5/Untitled.png)

- Run IntelliJ IDEA: *File → New Project*

![Untitled](Create%20a%20Customer%20Project%202df4a711ccc44a5e8093d52c50fa43a5/Untitled%201.png)

Choose the following directories - of course, you replace *%variable%* with your information:

| Project SDK | %AtomDir%\jre |
| --- | --- |
| Groovy Library | %ProjectRoot%\lib\groovy-2.4.13 |
| Project Name | YourCustomerName |
| Project Location | %ProjectRoot%\src |

![Project name and folder example](Create%20a%20Customer%20Project%202df4a711ccc44a5e8093d52c50fa43a5/Untitled%202.png)

Project name and folder example

Finally, you should have got something like this

![Untitled](Create%20a%20Customer%20Project%202df4a711ccc44a5e8093d52c50fa43a5/Untitled%203.png)

Your project folder has been set-up! 

Yes, you have got two `src` folders. That is ok for now.

![Untitled](Create%20a%20Customer%20Project%202df4a711ccc44a5e8093d52c50fa43a5/Untitled%204.png)

### Reference Project

> 💡 You will find a [reference project on GitHub](https://github.com/SchmidteServices/Boomi.Groovy.ReferenceProject.git).
> 

Don’t clone - **Download as ZIP!** You will want to use it as your project directory and put it under your or under the customer’s version control! ****

[Test your project setup](Create%20a%20Customer%20Project%202df4a711ccc44a5e8093d52c50fa43a5/Test%20your%20project%20setup%208cfaac77237644e29a4179d91c3cf94e.md)