#include <iostream>
#include <map>
#include <unordered_map>
#include <sstream>

using namespace std;

struct JSONParser {
    string key;         // only set up for map<string, string>
    string value;
};

istream& operator>>(istream& is, JSONParser& jp) {
    // "key": "value" } -or- "key": "value", ...} format      { checked before coming here
    string key, value;
    char firstQuot, secondQuot, colon, rightBracket;
    
    is >> firstQuot;
    getline(is, key, '\"'); 
    is >> colon >> secondQuot;
    getline(is, value, '\"');
    is >> rightBracket; 

    if(!is)
        return is;
    
    if (firstQuot !='\"' || colon!=':' || secondQuot!='\"') { // catching wrong format 
        is.clear(ios::failbit);
        return is;
    }  

    if (rightBracket == '}')
        is.eof();
    else if (rightBracket == ',')
           ;    // do nothing
    else {
        cerr << "Was expecting a ',' or '}', but found a " << rightBracket << '\n';
        is.clear(ios::failbit);
        return is;
    }
        
    jp = JSONParser{key, value};
    return is;
}


void fillMap(istream& is, unordered_map<string, string>& m) {
    char leftBracket = -1;
    if (is >> leftBracket && leftBracket!='{') { // before inputting, checking if first character is a {
        cerr << "was expecting a {" << '\n';
        is.unget();
        is.clear(ios::failbit);
    }

    for (JSONParser jp; is >> jp;)
        m[jp.key] = jp.value;
}

int main() {
    unordered_map<string, string> parserMap;
    string test = "{ \"jdelim\": \"one\", \"ericab\", \"two\", \"stephen\": \"three\"}";
    istringstream iss(test);
    
    fillMap(iss, parserMap);
    
    for (const auto& elem : parserMap) {
        cout << elem.first << ": " << elem.second << '\n';
    }

    if (parserMap.empty()) {
        cerr << "Map was not populated" << '\n';
    }

    return 0;
}