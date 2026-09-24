import { useCallback, useEffect, useState } from "react";
import AppShell from "../components/AppShell";
import JobCard from "../components/JobCard";
import ApplicationForm from "../components/ApplicationForm";
import { EmptyState, ErrorState, LoadingState } from "../components/AsyncState";
import { getJob, getJobs } from "../services/api";

const initialFilters = { keyword: "", location: "", type: "", level: "", remote: false };

export default function Jobs({ user, page, onNavigate, onLogout }) {
  const [filters, setFilters] = useState(initialFilters);
  const [jobs, setJobs] = useState([]);
  const [selectedJob, setSelectedJob] = useState(null);
  const [showApplication, setShowApplication] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [submitted, setSubmitted] = useState(false);

  const loadJobs = useCallback(async () => {
    setLoading(true);
    setError("");
    try {
      setJobs(await getJobs(filters));
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, [filters]);

  useEffect(() => {
    const timer = setTimeout(loadJobs, 0);
    return () => clearTimeout(timer);
  }, [loadJobs]);

  const updateFilter = (event) => {
    const { name, value, type, checked } = event.target;
    setFilters((current) => ({ ...current, [name]: type === "checkbox" ? checked : value }));
  };

  const viewJob = async (job) => {
    try {
      setSelectedJob(await getJob(job.id));
    } catch {
      setSelectedJob(job);
    }
    setShowApplication(false);
    setSubmitted(false);
  };

  const closeDetails = () => {
    setSelectedJob(null);
    setShowApplication(false);
    setSubmitted(false);
  };

  return (
    <AppShell {...{ user, page, onNavigate, onLogout }}>
      <main className="content">
        <div className="section-head">
          <div>
            <span className="eyebrow">OPPORTUNITIES</span>
            <h1>Find your next role</h1>
            <p>Explore curated local and remote opportunities that match your goals.</p>
          </div>
          <button className="button secondary" onClick={loadJobs}>Refresh</button>
        </div>

        <div className="filter-panel">
          <input name="keyword" value={filters.keyword} onChange={updateFilter} placeholder="Search title, company, or skill" />
          <input name="location" value={filters.location} onChange={updateFilter} placeholder="Location" />
          <select name="type" value={filters.type} onChange={updateFilter} aria-label="Job type">
            <option value="">All job types</option>
            <option value="Full-time">Full-time</option>
            <option value="Part-time">Part-time</option>
            <option value="Contract">Contract</option>
            <option value="Internship">Internship</option>
          </select>
          <select name="level" value={filters.level} onChange={updateFilter} aria-label="Experience level">
            <option value="">All experience levels</option>
            <option value="Entry level">Entry level</option>
            <option value="Mid level">Mid level</option>
            <option value="Senior level">Senior level</option>
          </select>
          <label className="check"><input type="checkbox" name="remote" checked={filters.remote} onChange={updateFilter} /> Remote only</label>
        </div>

        {loading ? <LoadingState text="Loading opportunities…" /> : error ? <ErrorState message={error} onRetry={loadJobs} /> : !jobs.length ? <EmptyState title="No roles found" text="Try adjusting your search or filters." /> : <div className="job-list">{jobs.map((job) => <JobCard key={job.id} job={job} onView={viewJob} />)}</div>}
      </main>

      {selectedJob && <div className="modal-backdrop" role="presentation" onClick={(event) => event.target === event.currentTarget && closeDetails()}>
        <section className="job-modal" role="dialog" aria-modal="true" aria-label={showApplication ? "Job application" : "Job details"}>
          <button className="modal-close" aria-label="Close" onClick={closeDetails}>×</button>
          {showApplication ? submitted ? <div className="success-state"><span className="eyebrow">APPLICATION SENT</span><h2>Your application is on its way.</h2><p>Track its progress from My Applications.</p><div className="application-summary"><span>{selectedJob.title}</span><span>{selectedJob.company}</span></div><button className="button" onClick={() => onNavigate("applications")}>View my applications</button></div> : <ApplicationForm job={selectedJob} user={user} onCancel={() => setShowApplication(false)} onSuccess={() => setSubmitted(true)} /> : <><span className="pill">{selectedJob.jobType}</span><h2>{selectedJob.title}</h2><h3>{selectedJob.company} · {selectedJob.location}</h3><p>{selectedJob.description}</p><p><strong>Experience:</strong> {selectedJob.experienceLevel}</p><p><strong>Salary:</strong> {selectedJob.salaryRange}</p><p><strong>Work style:</strong> {selectedJob.remoteAvailable ? "Remote friendly" : "On-site"}</p><div className="skills-list">{selectedJob.skills?.split(", ").map((skill) => <span key={skill}>{skill}</span>)}</div><div className="form-actions"><button className="button secondary" onClick={closeDetails}>Close</button><button className="button" onClick={() => setShowApplication(true)}>Apply now</button></div></>}
        </section>
      </div>}
    </AppShell>
  );
}