# Create a Customer Project

I recommend you create a project per customer - per Boomi Account.

* Create a folder on your local drive - we will call it `%ProjectRoot%`.
* Create a `lib` folder inside
  * [Extract the Groovy distribution](https://www.notion.so/Preparation-c8092a0505bf4333aa325b31ed2f0d6f?pvs=21) to the `lib` folder

![Untitled](<../MGF4Boomi - Groovy for Boomi bfadc9ce63764373816fa22fccd3cdc1/Create a Customer Project 2df4a711ccc44a5e8093d52c50fa43a5/Untitled.png>)

* Run IntelliJ IDEA: _File → New Project_

![Untitled](<../MGF4Boomi - Groovy for Boomi bfadc9ce63764373816fa22fccd3cdc1/Create a Customer Project 2df4a711ccc44a5e8093d52c50fa43a5/Untitled 1.png>)

Choose the following directories - of course, you replace _%variable%_ with your information:

| Project SDK      | %AtomDir%\jre                   |
| ---------------- | ------------------------------- |
| Groovy Library   | %ProjectRoot%\lib\groovy-2.4.13 |
| Project Name     | YourCustomerName                |
| Project Location | %ProjectRoot%\src               |

![Project name and folder example](<../MGF4Boomi - Groovy for Boomi bfadc9ce63764373816fa22fccd3cdc1/Create a Customer Project 2df4a711ccc44a5e8093d52c50fa43a5/Untitled 2.png>)

Project name and folder example

Finally, you should have got something like this

![Untitled](<../MGF4Boomi - Groovy for Boomi bfadc9ce63764373816fa22fccd3cdc1/Create a Customer Project 2df4a711ccc44a5e8093d52c50fa43a5/Untitled 3.png>)

Your project folder has been set-up!

Yes, you have got two `src` folders. That is ok for now.

![Untitled](<../MGF4Boomi - Groovy for Boomi bfadc9ce63764373816fa22fccd3cdc1/Create a Customer Project 2df4a711ccc44a5e8093d52c50fa43a5/Untitled 4.png>)

### Reference Project

> 💡 You will find a [reference project on GitHub](https://github.com/SchmidteServices/Boomi.Groovy.ReferenceProject.git).

Don’t clone - **Download as ZIP!** You will want to use it as your project directory and put it under your or under the customer’s version control! \*\*\*\*

[Test your project setup](<Test your project setup 8cfaac77237644e29a4179d91c3cf94e.md>)
