import java.util.Random;

// ���Ƽ� ����
class Poisson {
	public double getPoissonRandom(double mean) {
		// ���� ����
		Random r = new Random(System.currentTimeMillis());
		// exp : �ŵ�����
		double L = Math.exp(-mean);
		double k = 0;
		double p = 1.0;

		do {
			p = p * r.nextDouble();
			k++;
		} while (p > L);
		return k - 1;
	}
}

public class manyQueue {

	public static void main(String[] args) {

		/*
		 * �ð��� ���� �з�
		 *  time : �ùķ��̼��� ���� �ð� 
		 * tLimit : ��ǥ �ð� 
		 * tPump : ������ ���� ����ð�
		 * 
		 * ť�� ���� �з� 
		 * queue : �ٿ��� ��ٸ��� �ִ� ����� �� 
		 * totQue : ������ ť�� ��ü��, ��ü ������ �� ���ð�
		 * aveQueue : ��� ť�� ���� 
		 * arrive : 0 = ������ �������� �ʴ� ���, 1 = ������ ������ ���
		 * 
		 * ���Ƽۺ����� ���� ���� prArr : ������ 1�г� ������ Ȯ�� 
		 * mean : ���
		 *
		 */

		// ������
		Poisson p1 = new Poisson(); // ���Ƽۺ��� ������
		Random rand = new Random(System.currentTimeMillis()); // �õ尪 ������

		// �ð��� ���� ����
		double time = 0;
		double tLimit = 100;
		// double tPump = 0;
		double[] tPump = new double[2];
		double tStep = 1.0;

		// ť�� ���� ����
		int queue = 0;
		double aveQue = 0;
		int arrive = 0;
		int totQue = 0;
		
		/*
		 * ���Ƽۺ����� ���� ���� double mean = 1.0; 
		 * double mean = 2.0; 
		 * double mean = 4.0; 
		 * double mean = 6.0; 
		 * double mean = 8.0; 
		 * double mean = 10.0;
		 */
		double mean = 4.0;

		/*
		 * �յ������ ���� ���� double prArr = 0.17; // 1�ð� 10�� 
		 * double prArr = 0.33; // 1�ð� 20��
		 * double prArr = 0.5; // 1�ð� 30�� 
		 * double prArr = 0.67; // 1�ð� 40�� 
		 * double prArr = 0.83; // 1�ð� 50�� 
		 * double prArr = 1; // 1�ð� 60��
		 */
		double prArr = 0.33;

		System.out.println("�ð��� ���� : " + tStep + "��");
		System.out.println("��ǥ �ð� : " + tLimit + "��");
		System.out.println("������ 1�г� ������ Ȯ�� : " + prArr);
		System.out.println("���Ƽۺ��� ��� : " + mean);
		// System.out.println("TIME ARRIVAL QUEUE TPUMP1");
		System.out.println("TIME		ARRIVAL		QUEUE		TPUMP1		TPUMP2");
		// System.out.println("TIME		ARRIVAL		QUEUE		TPUMP1		TPUMP2		TPUMP3");
		// System.out.println("TIME		ARRIVAL		QUEUE		TPUMP1		TPUMP2		TPUMP3		TPUMP4");

		// 5ȸ �ݺ�
		for (int i = 1; i <= 5; i++) {
			// �ʱ�ȭ
			time = 0;
			arrive = 0;
			for(int k = 0; k < tPump.length; k++) {
				tPump[k] = 0;
			}
			queue = 0;
			totQue = 0;
			aveQue = 0;
			int j = 0;

			System.out.println("���� �� : " + i + "ȸ");
			// ���� �ð��� �� ������ �ݺ�
			while (time < tLimit) {
				// ���� �ð� ���
				time = time + tStep;
				arrive = 0;
				// ���� �߻� ���� ����
				double u = rand.nextDouble();
				// �������� prArr���� ������ �մ� �ϳ��� �߻���Ų��.
				if (u < prArr * tStep) {
					arrive = 1;
					queue = queue + arrive; // ť�� ����ϰ� �ִ� ����� ���� ����

				}
				j = 0; // tpump1,2
				while (j < tPump.length) {
					// ������ ���� ����ð��� �����ִ� ���
					if (tPump[j] > 0) {
						tPump[j] = tPump[j] - tStep;

						// ������ ���� ����ð��� ������ �Ǵ� ���� ����� �ϹǷ� 0���� �ʱ�ȭ�Ѵ�.
						if (tPump[j] < 0) 
							tPump[j] = 0;
						

					}

					// ������ ���� ����ð��� 0�� ���鼭 ť�� ����� ���ִ� ���
					if (tPump[j] == 0 && queue != 0) {
						queue = queue - 1; // ť�� �ִ� ����� ���ش�.
						tPump[j] = p1.getPoissonRandom(mean); // ���Ƽۺ����� ����Ͽ� ������ ���� ���缭�� �ð��� ����Ѵ�.
					}
					j++;
					// �������� ��ü ��
					totQue = totQue + queue;
					// ������ ��� ��� �ð�
					aveQue = totQue / (tLimit / tStep);
				}
				//System.out.println(time + "\t\t" + arrive + "\t\t" + queue + "\t\t" + tPump[0]	+"\t\t"	+ tPump[1]+ "\t\t" + tPump[2] + "\t\t" + tPump[3]);
				System.out.println(time + "\t\t" + arrive + "\t\t" + queue + "\t\t" + tPump[0]	+"\t\t"	+ tPump[1]);
			}
			System.out.println("������ ��� ���ð� : " + aveQue + "��");
			System.out.println("��ü ����ο��� : " + totQue + "��");
			System.out.println();
		}
	}
}