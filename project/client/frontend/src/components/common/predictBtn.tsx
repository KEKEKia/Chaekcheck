// interface
import { BtnInfo } from '../../interface/common';

function PredictBtn({
  width,
  height,
  defaultColor,
  selectedColor,
  fontColor,
  action,
  children,
}: BtnInfo) {
  return (
    <button
      type="button"
      className={`${width} ${height} hover:${selectedColor} hover:shadow-none ${defaultColor}  rounded-2xl shadow-lg shadow-BACKGROUND-600`}
      onClick={action}>
      <p className={`${fontColor}`}>{children}</p>
    </button>
  );
}
export default PredictBtn;
