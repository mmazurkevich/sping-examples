conn = new Mongo();
db = conn.getDB("myDatabase");
cursor = db.collection.find();
while ( cursor.hasNext() ) {
    printjson( cursor.next() );
}