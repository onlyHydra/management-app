# management-app

A company in charge of biking tournaments want a software to make their job of managing easier.
The app has :
1. Login function. After a succesful authentication a window will pop-up in which you can select all ongoing tournaments.
For each tournament you can filter them by the engine size it allows and at the same time it shows the number of participants .
2 Search function. The same window allows the user to search a team for it's participants in the tournaments, it will show 
the result in a different window/tabel. The result will also show all the tournaments the given team signed up for.
3. Sign up for a tournament function . For signing up the app will require the following informations : motor size of the contestant bike,
name of the contestant, name of the team the contestant belongs to .
4 Logout function. 

I decided to follow the client-server arhitecture model, with a database as a storage solution . 
The server is made with spring remoting for comunication , it has a Restfull API for forward expansion on front-end. 
All modifications are made in real time , and all clients are actualized with the new given information.

The client GUI is made using JavaFX.
