library(igraph)
library(sna)
library(tools)

setwd("/Users/yingweiliu/Desktop/big_data/")
a = list.files("Edges")
b <- file_path_sans_ext(a)
b<-as.numeric(b)
dir = paste("./Edges/",a,sep="")
n = length(dir)

fileIndex<- c(b)

getedges<- function(i) {
  edge <- read.table(paste("/Users/yingweiliu/Desktop/big_data/Edges/",as.character(fileIndex[i]),".edges",sep = ""),fill = TRUE)
  edge <- as.matrix(edge)
  return(edge)
}

getCircles<-function(i) {
  Circle <- file(paste("/Users/yingweiliu/Desktop/big_data/Circles/",as.character(fileIndex[i]),".circles",sep = ""))
  circles <- readLines(Circle)
  circleEdge <- transCircle(circles,i)
  return(circleEdge)
}

transCircle<-function(circle, i) {
  circleEdge <- c()
  for (i in 1:length(circle)) {
    circle_line <- unlist(strsplit(circle[i],split = "\t"))
    cirlce_line <- as.numeric(circle_line)
    
    curMatrix <- matrix(nrow = length(circle_line), ncol = 2)
    
    curMatrix[1,1] <- fileIndex[i]
    curMatrix[1,2] <- circle_line[2]
    
    for (j in 2:(length(cirlce_line)-1)) {
      curMatrix[j,1] <- circle_line[2]
      curMatrix[j,2] <- circle_line[j+1]
    }
    
    circleEdge <- rbind(circleEdge,curMatrix)
  }
  return (circleEdge)
}

totalEdges<-c()
for(i in 1:938){
  currEdge <- getedges(i)
  totalEdges <- rbind(totalEdges,currEdge)
  v
  currCircle <- getCircles(i)
  totalCircles <- rbind(totalEdges,currCircle)
}

new_graph <- graph.data.frame(d = totalEdges,directed = F)
g <- simplify(new_graph)
V(g)

plot(g,vertex.size=10,vertex.label = NA)

vertex_attr = vertex_attr(g)
head(vertex_attr$name,10)

gpage_rand <-page_rank(g)
head(gpage_rand$vector,10)

is.simple(g)
simplify(sg)


E(new_graph)$weight = rnorm(ecount(new_graph))
V(new_graph)$weight = rnorm(vcount(new_graph))
sg = induced.subgraph(new_graph,which(V(new_graph)$weight > 1.8))

V(sg)


is.simple(sg)
ng=simplify(sg)

vertex_attr = vertex_attr(sg)
head(vertex_attr$name,10)

gpage_rand <-page_rank(sg)
head(gpage_rand$vector,10)

graph.adj = rgraph(10,1,0.3,sg,F)
cgraph<-graph.adjacency(graph.adj,mode = "undirected")
plot(cgraph)
gden(graph.adj)
is_connected(sg)
connectedness(graph.adj)

com <- components(graph.adj)

hist(degree(graph.adj))
plot(degree.distribution(sg))


