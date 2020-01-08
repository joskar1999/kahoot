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


using namespace std;

#define ServerPort 1280



struct User {
    int userDesc;
    string login;
};

vector < User > Users;

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




//    int written_bytes;
//    while((written_bytes=write(clientDesc , "JP\n", 3))< 0) {
//        if (written_bytes == -1) {
//            fprintf(stderr, "Blad przy wysylaniu do znaku # do jednego z klientow");
//            return;
//        }
//    }
//    char buffer[4];
//    bzero(buffer,4);

//    cout << buffer << endl;



}

void clientConnection(int clientDesc){

    cout << "Zostalo nawiazane polaczenie z clientem, tworzony jest nowy watek" << endl;

    thread thd1(userThread, std::ref(clientDesc)); //Uruchomienie watka oraz przekazanie do niego deskryptora clienta
    thd1.join();
}

int main() {

    cout << "Server started" << endl;


    // Przygotowanie adresu przekazywanego do funkcji bind.
    // Połączenia przychodzące pod podany w funkcji bind adres będą trafiać do nasłuchującego gniazda.
    // Użycie INADDR_ANY określa, że połączenia przychodzące pod dowolny adres (i wskazany port) będą odbierane.
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

    // listen żąda od systemu operacyjnego by podane gniazdo rozpoczęło czekanie na przychodzące połączenia
    // drugi argument określa ilość połączeń które czekają na odebranie ich funkcja accept
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

//    auto currTime = std::time(nullptr);
//    char * text = std::ctime(&currTime);
//
//    int count = write(clientDesc, text, strlen(text));
//    if(count != (int) strlen(text))
//        perror("write failed");
//
//
//    shutdown(clientDesc, SHUT_RDWR);
//    close(clientDesc);

    return 0;
}
