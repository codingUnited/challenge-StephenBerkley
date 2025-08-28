#include <iostream>
#include <string>
#include <variant>
#include <unordered_map>
#include <sstream>

struct JSONParser {
    std::string key;
    std::variant<std::string, int, double, bool> value;
};

std::istream& operator>>(std::istream& is, JSONParser& jp)
{
    std::string key;
    std::variant<std::string, int, double, bool> value;

    char colon, ch;

    is >> ch;
    if (ch == '}')
    {
        is.clear(std::ios::failbit);
        return is;
    }

    if (ch != '\"')
    {
        is.clear(std::ios::failbit);
        return is;
    }

    std::getline(is, key, '\"');

    is >> colon >> ch;

    if (colon != ':')
        is.clear(std::ios::failbit);

    if (ch == '\"')
    {
        std::string strValue;
        std::getline(is, strValue, '\"');
        value = strValue;
    }
    else
    {
        // if not a string, make a token to check what type it is
        std::string token;
        token += ch;
        while (is.get(ch)) {
            if (ch == ',' || ch == '}')
                break;

            token += ch;
        }

        // try to parse into int, double, or bool
        if (token == "true")
            value = true;
        else if (token == "false")
            value = false;
        else if (token.find('.') != std::string::npos)  // !!! npos and find() should be used in conjuction. what this is saying here is we found a '.'
            value = std::stod(token);
        else
            value = std::stoi(token);


        if (ch == '}')
            is.unget(); // put '}' back

    }


    if (is.peek() == ',')
        is.get();

    else if (is.peek() == '}')
    {
        ; // do nothing
    }

    jp = JSONParser{key, value};
    return is;
}


template <typename T>
void fillMap(std::istream& is, std::unordered_map<std::string, T>& m) {
    char leftBracket = -1;
    if (is >> leftBracket && leftBracket!='{') { // before inputting, checking if first character is a {
        is.unget();
        is.clear(std::ios::failbit);
    }

    for (JSONParser jp; is >> jp;)
        m[jp.key] = jp.value;

}

int main() {

    std::unordered_map<std::string, std::variant<std::string, int, double, bool>> parserMap;
    std::string test = "{\"key1\":true, \"key2\":false, \"key4\":\"value\",\"key5\":101 }";
    std::istringstream iss(test);
    try{
        fillMap(iss, parserMap);

        auto printValue = [](const auto& v)
        {
            std::cout << v;
        };    // needed for std::visit

        for (const auto& elem : parserMap) {
            std::cout << elem.first << ": ";
            std::visit(printValue, elem.second); // std::visit is the way to print std::variant
            std::cout << '\n';
        }

        if (parserMap.empty()) {
            std::cerr << "Map was not populated" << '\n';
        }

        return 0;
    }
    catch (std::exception e) {
        std::cout << e.what();
        return 1;
    }
}