// chartjs
import { Chart as ChartJS, ArcElement, Tooltip } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

// interface
import { DoughnutProps } from '../../../interface/predictResult';

ChartJS.register(ArcElement, Tooltip);

export default function ChartPart({
  styleString = '',
  label,
  chartData,
}: DoughnutProps) {
  const backgroundColor = [
    'rgba(54, 162, 235, 1)',
    'rgba(75, 192, 192,1)',
    'rgba(255, 99, 132, 1)',
    'rgba(255, 206, 86, 1)',
  ];
  const borderColor = [
    'rgba(54, 162, 235, 1)',
    'rgba(75, 192, 192, 1)',
    'rgba(255, 99, 132, 1)',
    'rgba(255, 206, 86, 1)',
  ];
  const isNone = chartData.reduce((sum, curr) => sum + curr, 0) > 0;
  const data = {
    labels: ['최상', '상', '매입 불가', '중'],
    datasets: [
      {
        label,
        data: chartData,
        backgroundColor,
        borderColor,
        borderWidth: 1,
      },
    ],
  };

  return (
    <div className="w-[20rem] relative">
      {isNone ? (
        <Doughnut data={data} className={styleString} />
      ) : (
        <div className="rounded-full w-[19rem] h-[19rem] bg-BACKGROUND-300 flex justify-center items-center hover:">
          <div className="rounded-full w-[9.5rem] h-[9.5rem] bg-BACKGROUND-50" />
        </div>
      )}
      <div className="absolute font-medium left-[38%] top-[44%] text-center text-4xl">
        {label}
      </div>
    </div>
  );
}
