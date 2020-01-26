#include <cstdio>
#include <cstdlib>
#include <ctime>
#include <cstring>
#include <unistd.h>
#include <fcntl.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <curl/curl.h>
#include <thread>
#include <iostream>
#include <vector>
#include <fstream>
#include  <string>
#include <sstream>
#include <stdio.h>
#include <mutex>


using namespace std;

#define ServerPort 1280

int uniqueId = 1;

mutex userAmountMutex;

struct Question{
    string question;
    string a,b,c,d;
    string answer;
};

struct Quiz{
    string title;
    vector <Question> questions;
    int questionsAmount;
};


struct User {
    int userDesc;
    string login;
};

struct Game{
    int hostDesc;
    Quiz gameQuiz;
    int id;
    int userAmount;
    vector <User> gameUsers;
};


vector < User > Users;

vector <Quiz>  allQuiz;

vector <Game> allGames;

string convertToString(char* a, int size)
{
    int i;
    string s = "";
    for (i = 0; i < size; i++) {
        s = s + a[i];
    }
    return s;
}

string removeTxt(string s){
    if (!s.empty()) {
        s.resize(s.size() - 4);
    }
    return  s;
}

bool authentication(string username){

    bool isUnique = true;
    for(int i=0; i<Users.size(); i++){
        if(Users[i].login == username){
            isUnique = false;
            break;
        }
    }
    return isUnique;
}

void addAllQuiz(string quizArray[], int quizAmount){

    string questionText,a,b,c,d,answer;
    for(int i=0; i<quizAmount; i++){
        ifstream quizFile;
        quizFile.open(quizArray[i]);
        if( !quizFile.good() )
            cout << "Blad wczytania pliku" << endl;
        int questionAmount;
        quizFile >> questionAmount;
        string omitSpace;;
        quizFile >> omitSpace;
        Quiz quiz;
        quiz.questionsAmount = questionAmount;
        string title = removeTxt(quizArray[i]);
        quiz.title = title;

        for(int j=0;j < questionAmount;j++){
//            string questionText,a,b,c,d,answer;
            Question simpleQuestion;
            getline(quizFile,questionText);
            getline(quizFile,a);
            getline(quizFile,b);
            getline(quizFile,c);
            getline(quizFile,d);
            getline(quizFile,answer);
            simpleQuestion.question = questionText;
            simpleQuestion.a = a;
            simpleQuestion.b = b;
            simpleQuestion.c = c;
            simpleQuestion.d = d;
            simpleQuestion.answer = answer;

            quiz.questions.push_back(simpleQuestion);
        }
        allQuiz.push_back(quiz);
    }
}

void displayAllQuiz(){

    for(int i=0; i<allQuiz.size();i++){
        cout << "Quiz: " << allQuiz[i].title << endl;
        for(int j=0; j<allQuiz[i].questionsAmount;j++){
            cout << "Pytanie: " << allQuiz[i].questions[j].question << endl;
            cout << "1: " << allQuiz[i].questions[j].a << endl;
            cout << "2: " << allQuiz[i].questions[j].b << endl;
            cout << "3: " << allQuiz[i].questions[j].c << endl;
            cout << "4: " << allQuiz[i].questions[j].d << endl;
            cout << "odpowiedz: " << allQuiz[i].questions[j].answer << endl;
        }
        cout << endl;
    }
}



void chooseQuiz(int clientDesc){

//    for (auto&& [first,second] : mymap) {
        // use first and second
//    }

    // Czekamy az gracz (HOST) wybierze Quiz ktory chce zalozyc
    char quizChooseBuffer[100];
    ssize_t msgsize = recv(clientDesc, quizChooseBuffer, 100, 0);
    string selectedQuiz = convertToString(quizChooseBuffer, msgsize);
    cout << selectedQuiz << endl;
    for(int i = 0; i < allQuiz.size(); ++i) {
        if(selectedQuiz == allQuiz[i].title){
            Game game;
            game.hostDesc = clientDesc;
            game.gameQuiz = allQuiz[i];
            game.id = uniqueId;
            uniqueId++;
            allGames.push_back(game);
            break;
        }
    }
    write(clientDesc , "OK\n", 3);
}

const char * integerToChar(int temp){
    string tempString;
    tempString = to_string(temp) + "\n";
    const char * tempChar = tempString.c_str();
    return tempChar;
}

void sendingQuizInformation(int clientDesc){

    //Wyslanie informacji o rozpoczaciu komunikacji na temat liczby Quizow i pytan
    write(clientDesc , "QUIZ_HEADERS\n", 13);

//    Konwersja liczby Quizow z int na char aby moc wysylac komunikaty
    string quizAmount;
    string title;
    string questionStringAmount;
    quizAmount = to_string(allQuiz.size()) + "\n";
//        questionStringAmount = questionStringAmount + "\n";
    write(clientDesc , quizAmount.c_str(), strlen(quizAmount.c_str()));


    for(int i=0; i < allQuiz.size(); i++){
        //Wysylamy informacje o tytule
//        char* title = convertStringToChar(allQuiz[i].title);
        title = allQuiz[i].title + "\n";
        write(clientDesc , title.c_str(), strlen(title.c_str()));

        //Wysylamy  ilosc pytan
        questionStringAmount = to_string(allQuiz[i].questionsAmount) + "\n";
//        questionStringAmount = questionStringAmount + "\n";
        char const *questionCharAmountar = questionStringAmount.c_str();  //use char const* as target type
        write(clientDesc , questionCharAmountar, strlen(questionCharAmountar));

    }
}

void playerClient(int clientDesc){

    //Potwierdzamy ze nowy gracz zostal dodany
    write(clientDesc , "OK\n", 3);
    write(clientDesc,"GAMES_HEADERS\n",14);

    string gameTitle;
    string actuallStringGames;

    //Wysylamy liczbe dostepnych quizow
    actuallStringGames = to_string(allGames.size()) + "\n";
    //questionStringAmount = questionStringAmount + "\n";
    char const * actuallCharGames = actuallStringGames.c_str();  //use char const* as target type
    write(clientDesc , actuallCharGames, strlen(actuallCharGames));
    for(int i=0;i<allGames.size();i++){
        //Wysylamy tytul dostepnego Quizu
        gameTitle = allGames[i].gameQuiz.title + "\n";
        write(clientDesc, gameTitle.c_str(),strlen(gameTitle.c_str()));

        const char * actuallQuizQuestionAmount = integerToChar(allGames[i].gameQuiz.questionsAmount);
        write(clientDesc , actuallQuizQuestionAmount, strlen(actuallQuizQuestionAmount));

        write(clientDesc, "0\n",2);
//        const char * gamePlayersAmount = integerToChar(allGames[i].gameUsers.size());
//        write(clientDesc , gamePlayersAmount, strlen(gamePlayersAmount));

        const char * gameId = integerToChar(allGames[i].id);
        write(clientDesc , gameId, strlen(gameId));
    }

    char messageBuffer[100];
    ssize_t msgsize = recv(clientDesc, messageBuffer, 100, 0);
    string message = convertToString(messageBuffer, msgsize);
    memset(messageBuffer, 0, msgsize);
    if(message == "JOIN"){
        cout << "Gracz dolaczyl do gry" << endl;
        ssize_t msgsize2 = recv(clientDesc, messageBuffer, 100, 0);
        message = convertToString(messageBuffer, msgsize);
        int idGame = atoi(messageBuffer);
        memset(messageBuffer, 0, msgsize2);
        write(clientDesc,"OK\n",3);
        for(int i=0; i<allGames.size();i++){
            if(allGames[i].id == idGame){
//                userAmountMutex.lock();
                allGames[i].userAmount = allGames[i].userAmount + 1;
                for(int j=0; j< Users.size();j++) {
                    if (Users[j].userDesc == clientDesc){
                        allGames[i].gameUsers.push_back(Users[j]);
                        break;
                    }
                }
                cout << "Deskryptory graczy w grze o ID: " << allGames[i].id << endl;
                for(int k=0;k<allGames[i].gameUsers.size();k++) {
                    cout << allGames[i].gameUsers[k].userDesc << endl;
                    write(allGames[i].gameUsers[k].userDesc, "NEW_USER\n", 9);
                    const char * userInGame = integerToChar(allGames[i].gameUsers.size());
                    write(allGames[i].gameUsers[k].userDesc , userInGame, strlen(userInGame));
                }
                cout << "Koniec wyswietlania graczy" << endl;
                cout << "Liczba graczy w Grze: " << allGames[i].gameUsers.size()  << endl;
//                for(int p;p<allGames[i].gameUsers.size();p++){
//                    cout << "Wysylaj jakies info i sprawdz jaki rozmiar wiadomosci: ";
//                    ssize_t msgsize3 = write(allGames[i].gameUsers[p].userDesc , "NEW_USER\n", 9);
//                    cout << msgsize3 << endl;
//                    cout << "Wysylam new USERA" << endl;
//                    const char * userInGame = integerToChar(allGames[i].gameUsers.size());
//                    write(allGames[i].gameUsers[p].userDesc , userInGame, strlen(userInGame));
////                   userAmountMutex.unlock();
//                    }
                write(allGames[i].hostDesc, "NEW_USER\n", 9);
                const char * userInGame = integerToChar(allGames[i].gameUsers.size());
                write(allGames[i].hostDesc , userInGame, strlen(userInGame));
                break;
                }
            }
        }
        ssize_t msgsize2 = recv(clientDesc, messageBuffer, 100, 0);
        message = convertToString(messageBuffer, msgsize);
//    ssize_t msgsize3 = recv(clientDesc, messageBuffer, 100, 0);
//    message = convertToString(messageBuffer, msgsize3);
//    if(message == "START"){
//        cout
//    }


}



void hostClient(int clientDesc) {

    write(clientDesc, "OK\n", 3);
    sendingQuizInformation(clientDesc);
    chooseQuiz(clientDesc);
    char messageBuffer[100];
    string message;
    ssize_t msgsize3 = recv(clientDesc, messageBuffer, 100, 0);
    message = convertToString(messageBuffer, msgsize3);
    if (message == "START") {
        for (int i = 0; i < allGames.size(); i++) {
            if (allGames[i].hostDesc == clientDesc) {
                for (int j = 0; j < allGames[i].gameUsers.size(); ++j) {
                    write(allGames[i].gameUsers[j].userDesc, "START\n", 6);
                }
                break;
            }
        }
        write(clientDesc, "OK\n", 3);
    }
}

void userThread(int clientDesc){

    cout << "Nowy uzytkownik dodany" << endl;

//    send(clientDesc, "Hello, world!\n", 13, 0);
//    char *hello = "Hello";
    char usernameBuffer[100];
    while(1){

        ssize_t msgsize = recv(clientDesc, usernameBuffer, 100, 0);
        string username = convertToString(usernameBuffer, msgsize);
        memset(usernameBuffer, 0, 100);
        cout << username << endl;

        //Autentykacja loginu
        bool userLoginChecker = authentication(username);
        if(userLoginChecker){
            User connectedUser;
            connectedUser.login = username;
            connectedUser.userDesc = clientDesc;
            Users.push_back(connectedUser);
            write(clientDesc , "OK\n", 3);
            break;
        }
        else{
            write(clientDesc , "NO\n", 3);
            continue;
        }
    }
    memset(usernameBuffer, 0, sizeof usernameBuffer);
    //Autentykacja zostala zakończona, przechodzimy do wyboru pomiędzy Admin, Gracz a Host
    char roleDecisionBuffer[100];
    ssize_t msgsize = recv(clientDesc, roleDecisionBuffer, 100, 0);
    string selectedRole = convertToString(roleDecisionBuffer, msgsize);
    if(selectedRole == "HOST"){
        memset(roleDecisionBuffer, 0, sizeof roleDecisionBuffer);
        hostClient(clientDesc);
    }
    if(selectedRole == "USER"){
        memset(roleDecisionBuffer, 0, sizeof roleDecisionBuffer);
        playerClient(clientDesc);
    }
}

void clientConnection(int clientDesc){

    cout << "Zostalo nawiazane polaczenie z clientem, tworzony jest nowy watek" << endl;
//    write(clientDesc, "OK\n",3);

    thread thd1(userThread, clientDesc); //Uruchomienie watka oraz przekazanie do niego deskryptora clienta
    thd1.detach();
}

int main() {

    cout << "Server started" << endl;

    string quizFiles[5] = { "alkohole.txt", "pilka_nozna.txt",
                            "sasiedzi_polski.txt", "stolice.txt", "wojsko.txt" };

    addAllQuiz(quizFiles, 5);

//    displayAllQuiz();


    struct sockaddr_in serverAddr;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);
    serverAddr.sin_port = htons((uint16_t)ServerPort);

    int serverDesc = socket(AF_INET, SOCK_STREAM, 0);
    if(serverDesc == -1){
        perror("socket failed");
        return 1;
    }

    int fail = bind(serverDesc, (sockaddr*) &serverAddr, sizeof(serverAddr));
    if(fail){
        perror("bind failed");
    }

    fail = listen(serverDesc, 10);
    if(fail){
        perror("listen failed");
    }
    int connectDesc;
    while(1){
        // accept zwraca deskryptor pliku nawiązanego połączenia (czekając na to połączenie, jeśli żadnego nie ma w kolejce)
        connectDesc = accept(serverDesc, nullptr, nullptr);
        cout << "Deskryptor: " << connectDesc << endl;
//        if(connectDesc == -1){
//            perror("accept failed");
//        }
        clientConnection(connectDesc);

    }


    close(serverDesc);

//    shutdown(clientDesc, SHUT_RDWR);
//    close(clientDesc);

    return 0;
}