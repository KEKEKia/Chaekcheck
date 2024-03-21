import AlertIcon from '../../assets/icons/alertIcon';

interface Content {
  content: string;
  okAction: () => void;
}

function AlertContents({ content, okAction }: Content) {
  return (
    <div className="h-full bg-BACKGROUND-50 rounded shadow-md flex flex-col justify-center items-center">
      <AlertIcon styleString="w-[10vh]" />
      <p className="text-xl text-gray-500 my-2">{content}</p>
      <div className="flex justify-center mt-3 w-[90%] ">
        <button
          type="button"
          className="rounded-md w-full py-2 hover:bg-BUTTON1-900 bg-BUTTON1-500 text-BACKGROUND-50"
          onClick={okAction}>
          확인
        </button>
      </div>
    </div>
  );
}

export default AlertContents;
