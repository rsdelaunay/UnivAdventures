# Elaborado por : 122123 Rodrigo Delaunay, 111651 Lurdes Horta 

from dt import train_decision_tree
import csv
 
# py criado para treinar arvore de decisao com dados de treino e teste em CSV fornecidos pelo enunciado
# para vermos como a arvore de decisao funciona com os dados do enunciado

def load_csv_data(filepath): #carregar dados
    X = []
    y = []
    feature_names = []
    with open(filepath, 'r', encoding='utf-8') as file:
        reader = csv.reader(file, delimiter=';')
        header = next(reader) #saltar cabecalho
        
        # 'id;name;color;format;is_fruit'
        #queremos as features 'name', 'color', 'format'
        # O rótulo é 'is_fruit'
        
        try:
            name_idx = header.index('name')
            color_idx = header.index('color')
            format_idx = header.index('format')
            is_fruit_idx = header.index('is_fruit')
        except ValueError as e:
            print(f"Erro: Coluna esperada não encontrada no cabeçalho: {e}")
            print(f"Cabeçalho encontrado: {header}")
            return [], [], [] # retorna listas vazias em caso de erro

        # Nomes das features que serão usadas (excluindo 'id')
        # Pega do segundo ao penúltimo elemento do cabeçalho
        feature_names = [header[name_idx], header[color_idx], header[format_idx]]


        for row in reader:
            # features nas posições corretas (name, color, format)
            # Ignora a coluna 'id'
            current_features = [row[name_idx], row[color_idx], row[format_idx]]
            X.append(current_features)
            
            # Pega o rótulo 'is_fruit' e converte para int
            y.append(int(row[is_fruit_idx])) 
            
    return X, y, feature_names

def calculate_accuracy(tree, X_test, y_test): # percentagem que acertou predictions
    correct_predictions = 0
    for i in range(len(X_test)):
        prediction = tree.predict(X_test[i])
        if prediction == y_test[i]:
            correct_predictions += 1
    
    accuracy = correct_predictions / len(y_test) if len(y_test) > 0 else 0.0
    return accuracy

if __name__ == "__main__": 
    X_train, y_train, feature_names = load_csv_data('train.csv')
    if not X_train: # Verifica se o carregamento foi bem-sucedido
        print("Erro ao carregar dados de treino")
    else:
        print(f"Dados de Treino Carregados: {len(X_train)} exemplos.")
        print(f"Features Usadas: {feature_names}")

        tree = train_decision_tree(X_train, y_train, threshold=0.0) 
        print("Árvore de Decisão Treinada.")

        print("\nCarregar dados de teste...")
        X_test, y_test, _ = load_csv_data('test.csv')
        if not X_test:
            print("Erro ao carregar dados de teste")
        else:
            print(f"Dados de Teste Carregados: {len(X_test)} exemplos.")

            accuracy = calculate_accuracy(tree, X_test, y_test)
            print(f"\nAcertou a seguinte percentagem no Conjunto de Teste: {accuracy * 100:.2f}%")

            # Exemplos de predictions individua
            print("\nTestes de Prediction Individual:")
            test_item_1 = ['apple', 'red', 'circle']
            prediction_1 = tree.predict(test_item_1)
            print(f"Item: {test_item_1} -> Prediction: {prediction_1} (Esperado: 1)")

            test_item_2 = ['bomb', 'green', 'triangle'] # Exemplo do test.csv
            prediction_2 = tree.predict(test_item_2)
            print(f"Item: {test_item_2} -> Prediction: {prediction_2} (Esperado: -1)")
            
            test_item_3 = ['banana', 'yellow', 'curved'] 
            prediction_3 = tree.predict(test_item_3)
            print(f"Item: {test_item_3} -> Prediction: {prediction_3} (Esperado: 1)")

            test_item_4 = ['orange', 'blue', 'circle'] # Exemplo do train.csv (bomb)
            prediction_4 = tree.predict(test_item_4)
            print(f"Item: {test_item_4} -> Prediction: {prediction_4} (Esperado: -1)")