package com.tutorial.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.Spliterator;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.tutorial.streams.Paging.Page;
import com.tutorial.streams.Paging.Sesame;


public class LearnStreams {
	
	private LearnStreams() {}
	
	private void createFiniteStream() {
		Stream<String> empty = Stream.empty();
		Stream<Integer> singleElement = Stream.of(1);
		Stream<Integer> fromArray = Stream.of(1,2,3);
		var list = List.of("a","b","c");
		Stream<String> fromList = list.stream();
		Stream<String> fromParallelStreamList = list.parallelStream();
		
		System.out.println("Empty Stream: "+ empty.count());
		System.out.println("singleElement Stream: "+ singleElement.count());
		System.out.println("fromArray Stream: "+ fromArray.count());
		System.out.println("fromList Stream: "+ fromList.count());
		System.out.println("fromParallelStreamList Stream: "+ fromParallelStreamList.count());
	}
	
	private void createInfiniteStream() {
		Stream<Double> randoms = Stream.generate(Math::random);
		//randoms.forEach(System.out::println); ///This will print infinite random number untill u kill it.
		Stream<Integer> oddNumbers = Stream.iterate(1, n -> n+2);
		//oddNumbers.forEach(System.out::println);
		Stream<Integer> evenNumber = Stream.iterate(1, n-> n<20, n->n+1); //This iterate function is infinite or finite both
		evenNumber.forEach(System.out::println);
	}
	
	private void createMaxMinStreamFunction() {
		Stream<String> s = Stream.of("Apple","Cherry","Grappes");
		Optional<String> min = s.min((s1,s2) ->  s1.length() - s2.length()); 
		min.ifPresent(System.out::println);
		s.close();
		Stream<String> fruitStream = Stream.of("Apple","Cherry","Grappes");
		Optional<String> maxLengthFruit = fruitStream.max(Comparator.comparing(String::length));
		maxLengthFruit.ifPresent(System.out::println);
		fruitStream.close();
		
		Optional<?> empty = Stream.empty().min((s1,s2) -> 0); //Since stream is empty comparator will never called
		empty.ifPresent(System.out::println);
	}
	
	private void createFindAnyOrFindFirstStreamFunction(){
		Stream<String> finiteStream = Stream.of("Monkey","Tiger","Lion");
		Stream<String> infiniteStream = Stream.generate(() -> "Monkey");
		
	//	finiteStream.findAny().ifPresent(System.out::println);
		infiniteStream.findAny().ifPresent(System.out::println);
		finiteStream.filter(x-> x.contains("Lion")).findFirst().ifPresent(System.out::println);
	}
	private void createMatchingStreamFunction() { //Matching function return boolean
		var list = List.of("Monkey", "2","Chimp");
		Stream<String> infinite = Stream.generate(()->"chimp");
		Predicate<String> pred = x -> Character.isLetter(x.charAt(0));
		System.out.println(list.stream().anyMatch(pred)); //true
		System.out.println(list.stream().allMatch(pred));//false
		System.out.println(list.stream().noneMatch(pred));//false
		System.out.println(infinite.anyMatch(pred));//true
	}
	
	private void createReductionStream() { //combines a stream into single object.It processess all elements.
		
		//1. public T reduce(T identity, BinaryOperator<T> accumulator)
		//2. public Optional<T> reduce(BinaryOperator<T> accumulator)
		//3. public <U> U reduce(U identity, BiFunction<U, ? super T,U> accumulator, BinaryOperator<U> combiner)
		
		var array = new String[] {"W", "O", "L", "F"};
		var result = "";
		for(var s: array) {
			result += s;
		}
		//System.out.println(result);
		//1. Type first
		Stream<String> stream = Stream.of("W","O","L","F");
		//String word = stream.reduce("", (s,c) -> s+c);// empty string is the identity which concat all character
		String word2 = stream.reduce("", String::concat);
		System.out.println(word2);
		
		//2. Type second
		BinaryOperator<Integer> op = (a,b)-> a*b;
		Stream<Integer> empty = Stream.empty();
		Stream<Integer> oneElement = Stream.of(3);
		Stream<Integer> threeElement = Stream.of(3,5,6);
		
		empty.reduce(op).ifPresent(System.out::println); //no output optional is returned
		oneElement.reduce(op).ifPresent(System.out::println); // 3
		threeElement.reduce(op).ifPresent(System.out::println); // 90
		
		//3.Type third -> it allows java to create intermediate reduction and then combine them at the end.
		Stream<String> stream3 = Stream.of("WW","OO","LL","FF"); // run in parallel streams.
		int length = stream3.reduce(0, (i,s) -> i+s.length(), (a,b)-> a+b); // creates intermediate state (2,2,2,2) --> a+b(combiner)---> 8
		System.out.println(length);
	}
	
	private void createDuplicateRemovalStream() { //distinct will use to remove duplicate
		//distinct return stream.
		
		Stream<String> s = Stream.of("WW","WW","OO","OO","LL","LL","FF","FF");
		s.distinct().forEach(System.out::print);
	}
	
	private void createRestrictionStream() { 
		//1. public Stream<T> limit(long maxSize) it will skip the no of row or jump to max skip row.
		//2. public Stream<T> skip(long n)
		
		Stream<Integer> s = Stream.iterate(1, n -> n + 1); // print -> 1,2,3,4,5,6,7,8,9,10.....
		s.skip(5).limit(2).forEach(System.out::print);  // result -> it skip 1,2,3,4,5 and print 6,7 .limit is 2 so ans --> 67
	}
	
	private void createMappingOfStream() {
		//Map function is for data transformation for the next stream. 
		//1. public <R> Stream<R> map(Function<? super T, ? extends R> mapper)
		
		Stream<String> stream = Stream.of("Monkey","Gorillas","Chimpanzee");
		stream.map(x -> x.length()).forEach(System.out::println);
		stream.close();
		//2. flatMap --> capture the intermediate stream and perform action on it . Gives the top-level elements in a combined stream.
		List<String> zero = List.of();
		var one = List.of("Bonobo");
		var two = List.of("Mama","Gorilla", "Baby","Gorilla");
		Stream<List<String>> animals = Stream.of(zero,one,two);
		animals.flatMap(m -> m.stream()).forEach(System.out::println);
		//Stream<String> an =  animals.flatMap(m -> m.stream()).distinct();
	}
	
	private void create2IntermediateOperationOnStream() {
		var list = List.of("Rahul","Anu","Arati","Alex","Ritu","Moti");
		//filter check ---> if ok ---> send to next production sorted()--> Sorted wait untill all element came.
		//when all element came sorted started ---> send to limit ----> foreach
		list.stream().filter(x->x.length() == 4).sorted().limit(2).forEach(System.out::println);
		
	//	Stream.generate(() -> "Elsa").filter(n -> n.length() == 4).sorted().limit(2).forEach(System.out::println);// this will hang need to kill
		Stream.generate(() -> "Elsa").filter(n -> n.length() == 4).limit(2).sorted().forEach(System.out::println);//will print Elsa 2 times.
	}
	
	private void createPrimitiveStreams() {
		//1.IntStream --> int, short, byte and char.
		//2.LongStream --> long
		//3.DoubleStream --> double and float
		
		Stream<Integer> s = Stream.of(1,2,3);
		System.out.println(s.mapToInt(x-> x).sum()); // maoTont --> gives IntStream and perform sum with intstream and return.
		
		IntStream intstream = IntStream.of(1,2,3);
		OptionalDouble avg = intstream.average();
		System.out.println(avg.getAsDouble());
		
		var random = DoubleStream.generate(Math::random);
		var franction = DoubleStream.iterate(.5, d -> d/2);
		random.limit(3).forEach(System.out::println);
		franction.limit(3).forEach(System.out::println);

		IntStream range = IntStream.range(1, 6); //1,2,3,4,5
		range.forEach(System.out::println);
		
		IntStream rangeClosed = IntStream.rangeClosed(1, 5); //1,2,3,4,5
		rangeClosed.forEach(System.out::println);
	}
	
	private void createSpliteratorStream() {
		var stream = List.of("bird-","bunny-","cat-","dog-","fish-","lamb-","mouse-","camel-");
		System.out.println("Orignal Bag's has :- ");
		stream.forEach(System.out::println);
		Spliterator<String> orignalBag = stream.spliterator();
		Spliterator<String> rahulBag = orignalBag.trySplit(); // half data --> bird,bunny,cat,dog
		System.out.println("Rahul Bag's has :- ");
		rahulBag.forEachRemaining(System.out::println);
		
		System.out.println("Jills Bag's has :- ");
		Spliterator<String> jillsBag = orignalBag.trySplit(); // takes half of data from list --> fish,lamb
		jillsBag.tryAdvance(System.out::println); // Processes single element from spliterator if any remain.
		jillsBag.forEachRemaining(System.out::println); // Processes remaining elements in Spliterator.
		jillsBag.tryAdvance(System.out::println); // No item present in the jiils bag gives no output. 
		
		System.out.println("Emma Bag's has :- ");
		Spliterator<String> emmasBag = orignalBag.trySplit(); // half data --> mouse
		emmasBag.forEachRemaining(System.out::println);
		
		System.out.println("Orignal Bag's has remaining:- ");
		orignalBag.forEachRemaining(System.out::println);
		
	}
	
	private void createCollectingStream() {
		var numbers = List.of("1","2","3","4","5");
		String str = numbers.stream().collect(Collectors.joining(","));
		System.out.println(str);
		
	//	IntStream intStream = IntStream.of(1,2,3,4,5,6,7,8,9,10);
		
		var nums = List.of(1,2,3,4,5);
		double avg = nums.stream().collect(Collectors.averagingDouble(x-> x));
		System.out.println(avg);
		var newnums = nums.stream().collect(Collectors.groupingBy(x-> x>2)); //return a map of list true and false . {false=[1,2], true=[3,4,5]}
		System.out.println(newnums);
		var maxBy = nums.stream().collect(Collectors.maxBy(Comparator.comparingInt(x->x)));
		maxBy.ifPresent(System.out::println);
		var minBy = nums.stream().collect(Collectors.minBy(Comparator.comparingInt(x->x)));
		minBy.ifPresent(System.out::println);
		
		var vv = Stream.of("Lions","Tigers","Bears");
		Map<Integer, String> maps =  vv.collect(Collectors.toMap(String::length, k-> k, (s1,s2) -> s1+ "," + s2));
		System.out.println(maps);
		
		var vv2 = Stream.of("Lions","Tigers","Bears");
		TreeMap<Integer,String> treemap = 
				vv2.collect(Collectors.toMap(String::length, k->k, (s1,s2) -> s1+","+s2, TreeMap::new));
		System.out.println(treemap);
		
		var animals = List.of("Lions","Tigers","Bears","Lions","Bears","Monkey");
		Map<Integer, Set<String>> maps2 =  animals.stream().collect(Collectors.groupingBy(String::length,Collectors.toSet()));
		System.out.println(maps2);
		TreeMap<Integer,Set<String>> treemap2 = 
		animals.stream().collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toSet()));
		System.out.println(treemap2);
		
		//partitioning
		Map<Boolean, List<String>> partitionMaps1 = 
		animals.stream().collect(Collectors.partitioningBy(s -> s.length()>5)); //partition in two groups true and false.
		System.out.println(partitionMaps1);
		Map<Integer, Optional<Character>> charMap =
		animals.stream().collect(Collectors.groupingBy(String::length, Collectors.mapping(s-> s.charAt(0), Collectors.minBy((a,b) -> a-b))));
		System.out.println(charMap);
	}
	public final record Separations(Map<Integer,Set<String>> spaceSeparated, String commaSeparated) {}
	
	private void createTeeingCollectorsStream() {
		var animals = List.of("Lions","Tigers","Bears","Lions","Bears","Monkey");
		Separations result = animals.stream().collect(Collectors.teeing(
				Collectors.groupingBy(String::length, Collectors.toSet()), 
				Collectors.joining("**"), 	
				(s,c)-> new Separations(s,c)));
		System.out.println(result);
	}
	
	private void PraticeQuestion1() {
		var stream = Stream.iterate("", s -> s + "1");
		//System.out.println(stream.limit(2).map(x-> x+"2")); //[,1] --> 212 //map return the streams so it will print stream reference address.
		stream.limit(2).map(x-> x+"2").forEach(System.out::print); //--> 212
	}
	
	private void PraticeQuestion2() {
		Predicate<String> pred = s -> s.startsWith("g");
		var stream1 = Stream.generate(() -> "growl!");
		var stream2 = Stream.generate(() -> "growl!");
		var b1 = stream1.limit(2).anyMatch(pred);
		var b2 = stream2.limit(2).allMatch(pred);
		System.out.println(b1+ " "+ b2); //without limit operation it will hangs. And with limit give --> true true
		
		Predicate<String> pred2 = s -> s.length()>3;
		var stream3 = Stream.iterate("-", s -> !s.isEmpty(), s-> s+s);
		var b3 = stream3.noneMatch(pred2);
		var b4 = stream3.anyMatch(pred2);
		System.out.println(b3+" "+b4);// give exception as same stream is open again.
	}
	
	private void PracticeQuestion3() {
	//3.(a)
		double result1 = LongStream.of(6L,8L,10L)
				.mapToInt(x -> (int)x).boxed().collect(Collectors.groupingBy(x->x)).keySet().stream()
				.collect(Collectors.averagingInt(x-> x));
		System.out.println(result1);
	//3.(b)	
		var s = Stream.generate(()-> "meow");
		var match = s.allMatch(String::isEmpty);
		System.out.println(match);//return false 
	//3.(c)	
		var list = List.of(1,78,54,6,23,100);
		var copy = list.stream().sorted((a,b)-> b.compareTo(a)).collect(Collectors.toList()); //desc sort and collect the production list
		System.out.println(copy);
	//3.(d)
		var is = IntStream.empty();
		System.out.println(is.average()); //return type is OptionalDouble
		var is1 = IntStream.empty();
		System.out.println(is1.findAny()); //return type is OptionalInt
		var is2 = IntStream.empty();
		System.out.println(is2.sum()); //return type is int --> 0
	//3.(e)
		var s2 = LongStream.of(1,2,3);
		var opt = s2.map(n -> n * 10).filter(n -> n < 5).findFirst(); // what should write after line 298 that doesnt produce output and don't give error.
		opt.ifPresent(System.out::println);
		if(opt.isPresent()) {
			System.out.println(opt.getAsLong());
		}
	//3.(f)
		//var list2 =	Stream.iterate(1, x-> x++).limit(5).map(x -> x).collect(Collectors.joining()); what need to change to run the code and give 12345
		var list2 =	Stream.iterate(1, x-> ++x).limit(5).map(x -> ""+x).collect(Collectors.joining());
		System.out.println(list2);
	//3.(g)
		List<Integer> x1 = List.of(1,2,3);
		List<Integer> x2 = List.of(4,5,6);
		List<Integer> x3 = List.of();
		Stream.of(x1,x2,x3).flatMap(x -> x.stream()).map(x -> x+1).forEach(System.out::println);
	//3.(h)
		Stream<Integer> s4 = Stream.of(1);
		//Stream<Integer> is4 = s4.boxed();   has compilation error. boxed() is available for primitive Stream not Stream<Integer>. 
		//Correct syntax --> s4.map(x -> x).boxed();
		DoubleStream ds = s4.mapToDouble(x -> x);
	//	Stream<Integer> s5 = ds.mapToInt(x -> x); // compliation error cannot convert double to Int and Instream to Stream<Integer>.
		IntStream s5 = ds.mapToInt(x -> (int)x);
		s5.forEach(System.out::println);
	//3.(i)
		System.out.println("Question 3. (i) : ");
		var s6 = DoubleStream.of(1.2,2.4);
		s6.peek(System.out::println)
		.filter(x -> x > 2).count();
		
		var sumResult1 = DoubleStream.of(1, 2, 3, 4)
        .filter(e -> e > 2)
        .peek(e -> System.out.println("Filtered value: " + e)) //Peek is used to debug in between the production. Its a intermediate Operation.
        .map(e -> e * e)
        .peek(e -> System.out.println("Mapped value: " + e))
        .sum();
		System.out.println(sumResult1);
		
	//3.(j)
		var spliterator = Stream.generate(() -> "x").spliterator();
		spliterator.tryAdvance(System.out::print); // x
		
		var split = spliterator.trySplit();
		split.tryAdvance(System.out::print); //x
		
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to learning stream sessions.");
		LearnStreams obj = new LearnStreams();
		//obj.createFiniteStream();
		//obj.createInfiniteStream();
		//obj.createMaxMinStreamFunction();
		//obj.createFindAnyOrFindFirstStreamFunction();
		//obj.createMatchingStreamFunction();
		//obj.createReductionStream();
		//obj.createDuplicateRemovalStream();
		//obj.createRestrictionStream();
		//obj.createMappingOfStream();
		//obj.create2IntermediateOperationOnStream();
		//obj.createPrimitiveStreams();
		//obj.createSpliteratorStream();
		//obj.createCollectingStream();
		//obj.createTeeingCollectorsStream();
		//obj.PraticeQuestion1();
		//obj.PraticeQuestion2();
		obj.PracticeQuestion3();
		
		var monsters =Stream.of(new Sesame("Elmo",false));
		var people =Stream.of(new Sesame("Abby",true));
		//printPage(monsters, people);
		
	}

	private static void printPage(Stream<Sesame> monsters, Stream<Sesame> people) {
		Page page = Stream.concat(monsters, people)
				.collect(Collectors.teeing
						(Collectors.filtering(s -> s.name().startsWith("E"), Collectors.toList()), 
						Collectors.counting(),
						(l,c) -> new Page(l,c)
						));
		System.out.print(page);
		
	}

}
