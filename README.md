# backbone-baby-steps-spark-server

backend for this:
https://github.com/OlegPahhomov/backbone-baby-steps

inspiration from:
http://codebyexample.info/2012/04/29/backbone-in-baby-steps-part-2-5/

I just thought that setting up mongo and nodejs would be too long, so I sparked it up on sparkjava.


## Localhost guide. 1-2min
###### You need postgres db
###### 1. Change db connection in AppDataSource initLocalDbSource method
###### 2. Make sure AppDataSource DataSource dataSource = initLocalDbSource()
###### 3. Run Migration.java class main method
###### 4. Make sure Application.java is on port(8081)
###### 5. Run Application.java


## Heroku guide. 5-15min
###### You need heroku account and heroku toolbelt. If you're new I recommend checking out couple first points of https://devcenter.heroku.com/articles/getting-started-with-java#introduction
###### I understand you cannot do everything 1:1, but bear with me.
###### 1. You have installed git repo and you have created heroku app.
###### 2. Type "heroku config" it will give you DATABASE_URL it's postgres://[username]:[password]@[servername]:[port]/[databasename]
###### 3. Fill AppDataSource initHerokuDbsource() method with appropriate data and make sure that Datasource dataSource = initHerokuDatasource()
###### 4. Make sure Application.java main method uses: port(getHerokuAssignedPort())
###### 5. In pom.xml change <appName> tag to your app name
###### 6. In project folder run "mvn heroku:plugin"
###### 7. If it says it needs HEROKU_API_KEY, create new environmental variable, value of which you can find in your heroku account settings
###### 8. Run "heroku run migrate" (we defined it in "processTypes" in pom.xml, you cannot run flyway maven in heroku)  ((you sort of need to do it once))
###### 9. You need to make sure you have atleast 1 app running. Run "heroku ps:scale web=1"
###### 10. Run "heroku open"
###### 11. Add "/api/books" to the end of url

###### *. Along the process you might find useful to open another git bash or cmd window and type in project folder heroku logs --tail
###### *. For some reason, even if you do everything right, heroku crashes. Don't be discouraged and keep trying.

Usual deploy cycle is
* mvn heroku:plugin
* heroku ps:scale web=1
* heroku open

My app: https://morning-coast-1696.herokuapp.com/api/books