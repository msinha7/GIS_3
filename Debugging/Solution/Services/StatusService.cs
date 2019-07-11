using System.Collections.Generic;
using System.Linq;
using Solution.Model;
using Solution.Repos.Interfaces;
using Solution.Services.Interfaces;

/********************************************************************************
 *                                                                              *
 * This service doesn't seem to do what I was expecting. Could you take a look? *
 *                                                                              *
 *******************************************************************************/
namespace Solution.Services
{
    /// <summary>
    /// Contains all business specific rules for interacting with <see cref="Status"/> objects.  These rules define an
    /// Initial Transition as any <see cref="Transition"/> that has a null FromStatus property.
    /// </summary>
    public class StatusService : IStatusService
    {
        private ITransitionRepository TransitionRepo { get; set; }
        private IStatusRepository StatusRepo { get; set; }
        private ISubtypeRepository SubtypeRepo { get; set; }

        public StatusService(ITransitionRepository transitionRepo, IStatusRepository statusRepo, ISubtypeRepository subtypeRepo)
        {
            TransitionRepo = transitionRepo;
            StatusRepo = statusRepo;
            SubtypeRepo = subtypeRepo;
        }

        /// <summary>
        /// This method should return a list of distinct initial <see cref="Status"/> values available for a given <see cref="Subtype"/>.
        ///
        /// An initial <see cref="Status"/> is defined as a <see cref="Status"/> that exists as the ToStatus property of a
        /// <see cref="Transition" /> which also has a null FromStatus value.
        /// 
        /// </summary>
        /// <param name="subtype">The <see cref="Subtype"/> value to filter by.</param>
        /// <returns> An <see cref="IEnumerable{Status}"/> that represents all distinct Initial Status values available </returns>
        public IEnumerable<Status> GetInitialStatuses(Subtype subtype)
        {
            var allTransitions = TransitionRepo.GetAll();
            
            /*
            System.Console.WriteLine("All Transitions of {0}" ,subtype);
            
            foreach (var t in allTransitions)
            {
                if (t.FromStatus != null)
                {
                    
                    System.Console.WriteLine("From status {0}:", t.FromStatus.Name);
                    System.Console.WriteLine("to Status  {0}:", t.ToStatus.Name);
                    System.Console.WriteLine("Sub Type {0}:", t.Subtype.Name);
                    continue;
                }
                System.Console.WriteLine("NULL");
                System.Console.WriteLine("to Status  {0}:", t.ToStatus.Name);
                System.Console.WriteLine("Sub Type  {0}:", t.Subtype.Name);

                


            } 
            System.Console.WriteLine("...........................");
            
            */
            // Initial Transitions are defined as those with a null FromStatus value from config
            var initialTransitions = allTransitions.Where(t => t.FromStatus == null); 
            //added for subtypes
            //kind of AND operation
            var initialSubTypes = initialTransitions.Where(x => x.Subtype.Name == subtype.Name);
            //print statements for clarity where the code was going wrong
            /*
            System.Console.WriteLine("Variable Initial Transitions of {0} " , subtype);
            foreach (var it in initialTransitions)
            {  
                
                if (it.FromStatus!=null)
                {
                    System.Console.WriteLine("From Status {0} : ", it.FromStatus.Name);
                    System.Console.WriteLine("To Status  {0}: ", it.ToStatus.Name);
                    System.Console.WriteLine("Sub Type {0} : ", it.Subtype.Name);
                    continue;
                } 
                System.Console.WriteLine("NULL");
                System.Console.WriteLine("To Status  {0} : ", it.ToStatus.Name);
                System.Console.WriteLine("Sub Type : {0} ", it.Subtype.Name);
                
            } */
            //System.Console.WriteLine(it);
            //System.Console.WriteLine(initialTransitions);
            // Select all the ToStatus values from the initialTransitioned queried
            
            // return initialTransitions.Select(t => t.ToStatus); //original
            //debugged
            return initialSubTypes.Select(x => x.ToStatus);
        }
    }
}