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


using namespace std;

#define ServerPort 1280

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

vector < User > Users;

vector <Quiz>  allQuiz;

string convertToString(char* a, int size)
{
    int i;
    string s = "";
    for (i = 0; i < size; i++) {
        s = s + a[i];
    }
    return s;
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
        quiz.title = quizArray[i];

        for(int j=0;j < questionAmount;j++){
            string questionText,a,b,c,d,answer;
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

void hostClient(int clientDesc){
    write(clientDesc , "OK\n", 3);

}

void userThread(int &clientDesc){

    cout << "Siemano jestem uzytkownikiem" << endl;
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
    //Autentykacja zostala zakończona, przechodzimy do wyboru pomiędzy Admin, Gracz a Host
    char roleDecisionBuffer[100];
    ssize_t msgsize = recv(clientDesc, roleDecisionBuffer, 100, 0);
    string selectedRole = convertToString(roleDecisionBuffer, msgsize);
    if(selectedRole == "HOST"){
        hostClient(clientDesc);
    }
}

void clientConnection(int clientDesc){

    cout << "Zostalo nawiazane polaczenie z clientem, tworzony jest nowy watek" << endl;

    thread thd1(userThread, std::ref(clientDesc)); //Uruchomienie watka oraz przekazanie do niego deskryptora clienta
    thd1.join();
}

int main() {

    cout << "Server started" << endl;

    string quizFiles[5] = { "alkohole.txt", "pilka_nozna.txt",
                         "sasiedzi_polski.txt", "stolice.txt", "wojsko.txt" };

    addAllQuiz(quizFiles, 5);

//    displayAllQuiz();

    sockaddr_in serverAddr {};
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);
    serverAddr.sin_port = htons((uint16_t)ServerPort);

    int serverDesc = socket(PF_INET, SOCK_STREAM, 0);
    if(serverDesc == -1){
        perror("socket failed");
        return 1;
    }

    int fail = bind(serverDesc, (sockaddr*) &serverAddr, sizeof(serverAddr));
    if(fail){
        perror("bind failed");
        return 1;
    }

    fail = listen(serverDesc, 1);
    if(fail){
        perror("listen failed");
        return 1;
    }

    while(1){

        // accept zwraca deskryptor pliku nawiązanego połączenia (czekając na to połączenie, jeśli żadnego nie ma w kolejce)
        int connectDesc = accept(serverDesc, nullptr, nullptr);

        if(connectDesc == -1){
            perror("accept failed");
            return 1;
        }
        clientConnection(connectDesc);

    }


    close(serverDesc);

//    shutdown(clientDesc, SHUT_RDWR);
//    close(clientDesc);

    return 0;
}