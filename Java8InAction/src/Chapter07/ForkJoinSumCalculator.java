package Chapter07;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;
import java.util.concurrent.*;
import java.util.function.*;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {
	
	public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();
	
	public static void main(String[] args) {
		//1. RecursiveTask Ȱ�� : ������ Ǯ�� �̿��ϱ� ����, compute()�� ����
		/*
			compute()�� �ǻ��ڵ�
				if(�׽�ũ�� ����� �۰ų� ���̻� ���� �� �� ������){
					���������� �׽�ũ ���
				}else {
					�׽�ũ�� �� �����׽�ũ�� ����
					�׽�ũ�� �ٽ� �����׽�ũ�� ���ҵǵ��� �� �޼ҵ带 ���ȣ��
					��� �����׽�ũ�� ������ �Ϸ�� ������ ��ٸ�
					�� �����׽�ũ�� ����� ��ħ
				}
		  */
		
//        System.out.println("Iterative Sum done in: " + measurePerf(ParallelStreams::iterativeSum, 10_000_000L) + " msecs");
//        System.out.println("Sequential Sum done in: " + measurePerf(ParallelStreams::sequentialSum, 10_000_000L) + " msecs");
//        System.out.println("Parallel forkJoinSum done in: " + measurePerf(ParallelStreams::parallelSum, 10_000_000L) + " msecs" );
//        System.out.println("Range forkJoinSum done in: " + measurePerf(ParallelStreams::rangedSum, 10_000_000L) + " msecs");
//        System.out.println("Parallel range forkJoinSum done in: " + measurePerf(ParallelStreams::parallelRangedSum, 10_000_000L) + " msecs" );
//        System.out.println("ForkJoin sum done in: " + measurePerf(ForkJoinSumCalculator::forkJoinSum, 10_000_000L) + " msecs" );
        System.out.println("SideEffect sum done in: " + measurePerf(ParallelStreams::sideEffectSum, 10_000_000L) + " msecs" );
        System.out.println("SideEffect prallel sum done in: " + measurePerf(ParallelStreams::sideEffectParallelSum, 10_000_000L) + " msecs" );
	}
	
    public static <T, R> long measurePerf(Function<T, R> f, T input) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            R result = f.apply(input);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + result);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }
	
	public static final long THRESHOLD = 10_000;

    private final long[] numbers;
    private final int start;
    private final int end;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);
        leftTask.fork();
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return FORK_JOIN_POOL.invoke(task);
    }
}
