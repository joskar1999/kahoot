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
#include <pthread.h>
#include <iostream>

using namespace std;

#define ServerPort 1280

struct User {
    int clientDesc;
    char login[100];
};



int main(int argc, char ** argv) {
//    if(argc!=2){
//        printf("Usage: %s <port>\n", argv[0]);
//        return 1;
//    }
//    char * endp;
//    long port = strtol(argv[1], &endp, 10);
//    if(*endp || port > 65535 || port < 1){
//        printf("Usage: %s <port>\n", argv[0]);
//        return 1;
//    }

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
