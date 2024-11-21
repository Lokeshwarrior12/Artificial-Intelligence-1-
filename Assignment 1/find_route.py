import sys

class Vertex:
    def __init__(self, city, parent=None, cost=0, heuristic=0):
        self.city = city
        self.parent = parent
        self.cost = cost
        self.heuristic = heuristic

class Map:
    def __init__(self):
        self.connections = {}

    def add_connection(self, start, end, distance):
        if start not in self.connections:
            self.connections[start] = []
        if end not in self.connections:
            self.connections[end] = []

        self.connections[start].append((end, distance))
        self.connections[end].append((start, distance))

def load_graph_names(filename):
    graph = Map()
    with open(filename, 'r') as file:
        line = file.readline().strip()
        while line != "END OF INPUT":
            data = line.split()
            origin, destination, cost = data[0], data[1], float(data[2])
            graph.add_connection(origin, destination, cost)
            line = file.readline().strip()
    return graph

def load_heuristic_values(filename):
    heuristic = {}
    with open(filename, 'r') as file:
        line = file.readline().strip()
        while line != "END OF INPUT":
            city, heuristic_value = line.split()
            heuristic[city] = float(heuristic_value)
            line = file.readline().strip()
    return heuristic

def ucs(graph, start_city, goal_city):
    fringe = []
    visited = set()
    nodes_popped = 0
    nodes_expanded = 0
    nodes_generated = 1

    start_vertex = Vertex(start_city)
    goal_vertex = Vertex(goal_city)

    fringe.append(start_vertex)

    while fringe:
        fringe.sort(key=lambda vertex: vertex.cost)
        current_vertex = fringe.pop(0)
        nodes_popped += 1

        if current_vertex.city == goal_vertex.city:
            return current_vertex, nodes_popped, nodes_expanded, nodes_generated

        if current_vertex.city not in visited:
            visited.add(current_vertex.city)
            nodes_expanded += 1

            connections = graph.connections[current_vertex.city]
            i = 0
            while i < len(connections):
                connection = connections[i]
                neighbor_city, distance = connection
                neighbor_vertex = Vertex(neighbor_city, current_vertex, current_vertex.cost + distance)
                fringe.append(neighbor_vertex)
                nodes_generated += 1
                i += 1

    return None, nodes_popped, nodes_expanded, nodes_generated

def a_star(graph, start_city, goal_city, heuristic_data):
    fringe = []
    visited = set()
    nodes_popped = 0
    nodes_expanded = 0
    nodes_generated = 1

    start_vertex = Vertex(start_city, None, 0, heuristic_data.get(start_city, 0))
    goal_vertex = Vertex(goal_city)

    fringe.append(start_vertex)

    while fringe:
        fringe.sort(key=lambda vertex: vertex.cost + vertex.heuristic)
        current_vertex = fringe.pop(0)
        nodes_popped += 1

        if current_vertex.city == goal_vertex.city:
            return current_vertex, nodes_popped, nodes_expanded, nodes_generated

        if current_vertex.city not in visited:
            visited.add(current_vertex.city)
            nodes_expanded += 1

            connections = graph.connections[current_vertex.city]
            i = 0
            while i < len(connections):
                connection = connections[i]
                neighbor_city, distance = connection
                neighbor_vertex = Vertex(neighbor_city, current_vertex, current_vertex.cost + distance, heuristic_data.get(neighbor_city, 0))
                fringe.append(neighbor_vertex)
                nodes_generated += 1
                i += 1

    return None, nodes_popped, nodes_expanded, nodes_generated

def main():
    command_args = sys.argv

    if len(command_args) < 4:
        print("Insufficient input provided.")
        sys.exit(1)

    input_filename = command_args[1]
    start_city = command_args[2]
    goal_city = command_args[3]
    heuristic_doc = command_args[4] if len(command_args) == 5 else None

    graph = load_graph_names(input_filename)

    if heuristic_doc:
        heuristic_data = load_heuristic_values(heuristic_doc)
        result, nodes_popped, nodes_expanded, nodes_generated = a_star(graph, start_city, goal_city, heuristic_data)
    else:
        result, nodes_popped, nodes_expanded, nodes_generated = ucs(graph, start_city, goal_city)

    print("Nodes Popped:", nodes_popped)
    print("Nodes Expanded:", nodes_expanded)
    print("Nodes Generated:", nodes_generated)

    if result:
        path = []
        total_distance = result.cost
        for current_node in iter(lambda: result.parent, None):
            path.insert(0, (result.parent.city, result.city, result.cost - result.parent.cost))
            result = result.parent

        print("Distance:", total_distance, "km")
        print("Route:")
        for item in path:
            print(f"{item[0]} to {item[1]}, {item[2]} km")
    else:
        print("Distance: infinity")
        print("Route: None")

if __name__ == '__main__':
    main()
