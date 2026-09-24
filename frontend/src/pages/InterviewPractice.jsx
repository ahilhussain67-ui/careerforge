import { useState } from "react";
import AppShell from "../components/AppShell";
import ProgressBar from "../components/ProgressBar";
import { evaluateAnswer, interviewBank } from "./interviewData";

export default function InterviewPractice({ user, page, onNavigate, onLogout }) {
  const [topic, setTopic] = useState("");
  const [index, setIndex] = useState(0);
  const [answer, setAnswer] = useState("");
  const [results, setResults] = useState([]);
  const [finished, setFinished] = useState(false);
  const questions = interviewBank[topic] || [];

  const chooseTopic = (nextTopic) => {
    setTopic(nextTopic); setIndex(0); setAnswer(""); setResults([]); setFinished(false);
  };
  const next = () => {
    const result = evaluateAnswer(questions[index], answer);
    const nextResults = [...results, result]; setResults(nextResults); setAnswer("");
    if (index === questions.length - 1) setFinished(true); else setIndex(index + 1);
  };
  const restart = () => { setIndex(0); setResults([]); setAnswer(""); setFinished(false); };
  const totalPoints = results.reduce((sum, result) => sum + result.points, 0);
  const maxPoints = questions.length * 3;
  const percentage = maxPoints ? Math.round((totalPoints / maxPoints) * 100) : 0;
  const strong = results.filter((result) => result.points === 3).length;
  const good = results.filter((result) => result.points === 2).length;
  const partial = results.filter((result) => result.points === 1).length;
  const weak = results.filter((result) => result.points === 0).length;

  return <AppShell {...{ user, page, onNavigate, onLogout }}><main className="content practice"><div className="page-heading"><span className="eyebrow">INTERVIEW PRACTICE</span><h1>Practise saying it clearly.</h1><p>Build confidence by answering common technical interview questions out loud or in writing.</p></div>{!topic ? <section className="topic-picker">{Object.keys(interviewBank).map((name) => <button className="quick-card" key={name} onClick={() => chooseTopic(name)}><h3>{name}</h3><p>{interviewBank[name].length} focused questions</p><span>Start practice →</span></button>)}</section> : finished ? <section className="result-card"><span className="eyebrow">SESSION COMPLETE</span><h2>{totalPoints} / {maxPoints}</h2><p>{percentage}% content score based on the concepts in your answers.</p><div className="practice-results"><div><strong>{strong}</strong><span>Strong answers</span></div><div><strong>{good}</strong><span>Good answers</span></div><div><strong>{partial}</strong><span>Partial answers</span></div><div><strong>{weak}</strong><span>Needs improvement</span></div></div><div className="feedback-list">{results.map((result, resultIndex) => <article className="feedback-item" key={`${questions[resultIndex].question}-${resultIndex}`}><strong>{resultIndex + 1}. {result.label}</strong><p>{result.explanation}</p></article>)}</div><button className="button" onClick={restart}>Practise again</button><button className="text-button" onClick={() => chooseTopic("")}>Choose another topic</button></section> : <section className="practice-card"><div className="practice-top"><span>{topic} · Question {index + 1} of {questions.length}</span><ProgressBar value={index / questions.length * 100} /></div><h2>{questions[index].question}</h2><label>Your answer<textarea rows="8" value={answer} onChange={(event) => setAnswer(event.target.value)} placeholder="Structure your response: explain the concept, give an example, and share why it matters." /></label><div className="lesson-actions"><button className="button secondary" onClick={() => chooseTopic("")}>Exit session</button><button className="button" onClick={next}>{index === questions.length - 1 ? "Finish practice" : "Next question"}</button></div></section>}</main></AppShell>;
}
