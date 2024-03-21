export default function LegendsPart(props: {
  bgColor: string;
  children: string;
}) {
  const { bgColor, children } = props;
  return (
    <div className="flex justify-center">
      <div className={`w-8 h-8 m-1 ${bgColor} `} />
      <div className="text-center my-auto mx-1 ">{children}</div>
    </div>
  );
}
